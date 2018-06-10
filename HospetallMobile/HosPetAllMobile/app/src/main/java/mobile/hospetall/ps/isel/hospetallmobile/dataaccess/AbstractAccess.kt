package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

abstract class AbstractAccess<T> (private val queue: RequestQueue){

    fun get(uri: String, onSuccess: Response.Listener<T>, onError: Response.ErrorListener) {
        val cache = queue.cache.get(uri)?.data
        cache?.apply{
            onSuccess.onResponse(parse(JSONObject(String(this))))
        }

        //TODO : Database

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
                        uri,
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
                        uri,
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

    open fun post(
            uri: String,
            json: JSONObject,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener
    ) : Request<JSONObject>? =
            queue.add(JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    json,
                    Response.Listener {
                        onSuccess.onResponse(parse(it))
                    },
                    onError
            ))

    abstract fun parse(json: JSONObject) : T
}