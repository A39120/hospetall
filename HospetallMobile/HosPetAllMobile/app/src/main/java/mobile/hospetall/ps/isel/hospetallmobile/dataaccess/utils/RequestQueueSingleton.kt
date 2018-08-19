package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import mobile.hospetall.ps.isel.hospetallmobile.security.TokenUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import org.json.JSONObject

class RequestQueueSingleton(context: Context) {
    companion object {
        private const val NETWORK_TAG = "ACCESS/NETWORK_REQUEST"
        private const val DATABASE_TAG = "ACCESS/DATABASE_REQUEST"

        @Volatile
        private var singleton: RequestQueueSingleton? = null

        @Synchronized
        fun getInstance(context: Context? = null): RequestQueueSingleton {
            if (singleton == null) {
                if (context == null)
                    throw IllegalAccessException("The request queue cannot be initiated anywhere else except the application.")

                singleton = RequestQueueSingleton(context)
            }
            return singleton!!
        }

    }

    val requestQueue by lazy { Volley.newRequestQueue(context) }
    val imageLoader by lazy {
        ImageLoader(
            requestQueue,
            object: ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap { return cache.get(url) }
                override fun putBitmap(url: String, bitmap: Bitmap) { cache.put(url, bitmap) }
            }
        )
    }

    private var token : String? = null
    private var expiration: Long = -1

    private fun setToken(token: String, expiration: Long){
        this.token = token
        this.expiration = expiration
    }

    private fun setToken(sharedPreferences: SharedPreferences){
        val token = sharedPreferences.getString(SharedPrefKeys.TOKEN, null)
        val exp = sharedPreferences.getLong(SharedPrefKeys.EXPIRATION, -1)
        setToken(token, exp)
    }

    private fun getToken() =
        if(expiration >= 0)
            token
        else null

    fun get(context: Context,  url: String, onResponse: (JSONObject) -> Unit, onError: Response.ErrorListener) =
            request(context, url, Request.Method.GET, null, onResponse, onError)


    fun post(context: Context, url: String, postObject: JSONObject?,  onResponse: (JSONObject?) -> Unit, onError: Response.ErrorListener) =
            request(context, url, Request.Method.POST, postObject, onResponse, onError)

    fun put(context: Context,  url: String, putObject: JSONObject?, onResponse: (JSONObject) -> Unit, onError: Response.ErrorListener) =
            request(context, url, Request.Method.PUT, putObject, onResponse, onError)

    fun delete(context: Context, url: String, onResponse: (JSONObject) -> Unit, onError: Response.ErrorListener) =
            request(context, url, Request.Method.DELETE, null, onResponse, onError)

    private fun request(context: Context, url: String, method: Int, reqObject: JSONObject?, onResponse: (JSONObject) -> Unit, onError: Response.ErrorListener): Request<JSONObject>?{
        if( singleton!= null && singleton?.getToken() != null)
            TokenUtils.defineTokenIfExpired(context, { setToken(it.token, it.expiresIn)})

        return singleton?.requestQueue?.add(
                JsonHalRequest(
                    singleton?.getToken()!!, method, url, reqObject,
                    Response.Listener {
                        Log.i(NETWORK_TAG, "Request with $method to $url")
                        it?.let(onResponse)
                    },
                    onError
                )
        )
    }

}