package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.net.Uri
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

abstract class AbstractAccess<T> (private val queue: RequestQueue){


    fun get(uri: String, onSuccess: Response.Listener<T>, onError: Response.ErrorListener) {
        getFromUri(uri, onSuccess, onError)
    }

    fun getList(uri: String, property: String, onSuccess: Response.Listener<List<T>>, onError: Response.ErrorListener) {
        getCollectionFromUri(uri, property, onSuccess, onError)
    }

    open fun getFromUri(
            uri: String,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener)
    {
        queue.add(
                JsonObjectRequest(
                        uri.toString(),
                        null,
                        Response.Listener {
                                onSuccess.onResponse(parse(it))
                        },
                        onError
        ))
    }



    open fun getCollectionFromUri(
                                     uri: String,
                                     property: String,
                                     onSuccess: Response.Listener<List<T>>,
                                     onError: Response.ErrorListener
    ) {
        val embedded = "_embedded"
        queue.add(
                JsonObjectRequest(
                        uri.toString(),
                        null,
                        Response.Listener {
                            val jsonArr = it.optJSONObject(embedded)?.optJSONArray(property)
                            if(jsonArr != null) {
                                val list =
                                        List(jsonArr.length(), { jsonArr.get(it) as JSONObject })
                                                .map { parse(it) }

                                onSuccess.onResponse(list)
                            }
                        },
                        onError
                )
        )
    }

    abstract fun parse(json: JSONObject) : T
}