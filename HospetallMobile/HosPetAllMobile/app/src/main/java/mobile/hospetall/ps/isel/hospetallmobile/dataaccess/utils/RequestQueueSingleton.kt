package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class RequestQueueSingleton(context: Context) {
    companion object {
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
        ImageLoader(requestQueue,
                object: ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)
                    override fun getBitmap(url: String): Bitmap {
                        return cache.get(url)
                    }
                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
    }
}