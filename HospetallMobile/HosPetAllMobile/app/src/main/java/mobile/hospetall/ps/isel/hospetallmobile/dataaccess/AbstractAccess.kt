package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.utils.AsyncTaskImpl
import org.json.JSONObject

/**
 * Abstract class with the purpose of accessing information
 * from multiple sources.
 *
 * [RequestQueue] will be used to communicate with the API,
 * or to store information in a cache.
 */
abstract class AbstractAccess<T> (
        private val queue: RequestQueue
) {
    companion object {
        const val TAG = "HPA/DA/ABSTRACT"
    }

    /**
     * Get function to obtain an object with a certain uri,
     * and pass it to a callback if there is an answer.
     *
     * @param uri: the chosen id for most objects;
     * @param onSuccess: success callback, will receive the
     * object when successful
     * @param onError: error callback, will receive the object
     * when a failure occurs, if this parameter is null then
     * only an log message will be written.
     */
    fun get(uri: String, onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null) {
        val cache = queue.cache.get(uri)?.data
        cache?.apply{
            Log.i(TAG, "Got result for $uri from cache.")
            onSuccess.onResponse(
                    parse(JSONObject(String(this))))
            return
        }

        //Start async task to deal with data from database.
        val task = AsyncTaskImpl(
                uri,
                this::getFromDatabase,
                {
                    if(it != null){
                        Log.i(TAG, "Got result for $uri from database.")
                        onSuccess.onResponse(it)
                    } else {
                        getFromUri(uri, onSuccess, onError)
                    }
                }
        )

        task.execute()
    }

    /**
     * Get function to obtain an multiple objects represented
     * with an uri, and pass it to a callback if there is an
     * answer.
     *
     * @param uri: the chosen id for most objects;
     * @param property: string that represents a json property
     * where the list is contained;
     * @param onSuccess: success callback, will receive the
     * list of objects when successful
     * @param onError: error callback, will receive the object
     * when a failure occurs, if this parameter is null then
     * only an log message will be written.
     */
    fun getList(uri: String,
                property: String,
                onSuccess: Response.Listener<List<T>>,
                onError: Response.ErrorListener) {

        getCollectionFromUri(
                uri,
                property,
                onSuccess,
                onError)
    }

    /**
     * Gets an object directly from the Web API represented
     * with the [uri].
     */
    open fun getFromUri(
            uri: String,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null)
    {
        queue.add(
                JsonObjectRequest(
                        uri,
                        null,
                        Response.Listener {
                            Log.i(TAG, "Got object from $uri.")
                            val obj = parse(it)

                            Log.i(TAG, "Inserting object into database with uri: $uri.")
                            insertInDatabase(obj)

                            onSuccess.onResponse(obj)
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Failed to send get request from $uri")
                            onError?.run { onErrorResponse(it) }
                        }
        ))
    }

    /**
     * Tries to obtain an object from the database.
     *
     * @param uri: id of the object stored in the database;
     * @return object if present in the database, or null if not
     * present
     */
    abstract fun getFromDatabase(uri: String) : T?

    /**
     * Inserts object inside the database.
     *
     * @param obj: Object to insert inside the database
     */
    abstract fun insertInDatabase(obj : T)

    abstract fun insertCollectionInDatabase(list : List<T>)

    /**
     * Gets a list of objects directly from the Web API
     * represented with the [uri].
     */
    open fun getCollectionFromUri(
                                     uri: String,
                                     property: String,
                                     onSuccess: Response.Listener<List<T>>,
                                     onError: Response.ErrorListener? = null
    ) {
        val embedded = "_embedded"
        queue.add(
                JsonObjectRequest(
                        uri,
                        null,
                        Response.Listener {
                            Log.i(TAG, "Got json list data from $uri")
                            val jsonArr = it.optJSONObject(embedded)?.optJSONArray(property)
                            if(jsonArr != null) {
                                val list =
                                        List(jsonArr.length(), { jsonArr.get(it) as JSONObject })
                                                .map { parse(it) }
                                Log.i(TAG, "Inserting new data from $uri list into database.")
                                insertCollectionInDatabase(list)
                                onSuccess.onResponse(list)
                            }
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Failed to send get request for list from $uri")
                            onError?.run { onErrorResponse(it) }
                        }
                )
        )
    }

    /**
     * Makes a post request.
     * TODO: Test this.
     * @param uri: target of the post
     * @param json: JSON object that contains the object to
     * post;
     * @return the request made for the post.
     */
    open fun post(
            uri: String,
            json: JSONObject,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null
    ) : Request<JSONObject>? =
            queue.add(JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    json,
                    Response.Listener {
                        Log.i(TAG, "Successfully sent post request to $uri.")
                        onSuccess.onResponse(parse(it))
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Failed to send post request to $uri.")
                        onError?.run { onErrorResponse(it) }
                    }
            ))

    /**
     * Parses [JSONObject] into a [T] object.
     *
     * @param json: the json object to be parsed;
     * @return instance defined object.
     */
    abstract fun parse(json: JSONObject) : T
}