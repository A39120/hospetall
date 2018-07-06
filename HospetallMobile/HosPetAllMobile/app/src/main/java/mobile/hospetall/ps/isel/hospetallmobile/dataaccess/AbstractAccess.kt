package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.BaseDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import org.json.JSONObject


/**
 * Abstract class with the purpose of accessing information
 * from multiple sources.
 *
 * [RequestQueue] will be used to communicate with the API,
 * or to store information in a cache.
 */
abstract class AbstractAccess<T, V : BaseDao<T>> (
        application: HospetallApplication
) {
    companion object {
        const val TAG = "HPA/DA/ABSTRACT"

        class InsertAsyncTask<T, V : BaseDao<T>>(private val dao: V)
            : AsyncTask<T, Unit, Unit>() {

            override fun doInBackground(vararg params: T?) {
                params[0]?.let { dao.insertOrUpdate(it) }
            }

        }

        class GetAsyncTask<T, V : BaseDao<T>>(
                private val dao: V,
                private val listener: Response.Listener<T>
        ) : AsyncTask<String, Unit, T>() {

            override fun doInBackground(vararg params: String?): T? {
                return params[0]?.let {
                    return@let dao.get(it)
                }
            }

            override fun onPostExecute(result: T?) {
                super.onPostExecute(result)
                listener.onResponse(result)
            }
        }
    }

    protected val queue = application.requestQueueSingleton.requestQueue
    protected val database = application.database

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
    fun get(uri: String,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null) {
        val cache = queue.cache.get(uri)?.data

        if(cache != null) {
            Log.i(TAG, "Got result for $uri from cache.")
            onSuccess.onResponse(parse(JSONObject(String(cache))))
        } else {
            getFromDatabase(uri, onSuccess, onError)
        }
    }

    /**
     * Gets an object directly from the Web API represented
     * with the [uri].
     */
    open fun getFromUri(uri: String, onSuccess: Response.Listener<T>, onError: Response.ErrorListener?)  {
        queue.add(
                JsonObjectRequest(
                        uri,
                        null,
                        Response.Listener {
                            Log.i(TAG, "Got object from $uri.")
                            val obj = parse(it)
                            insertInDatabase(obj)
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Failed to send get request from $uri: ${it.message}")
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
    fun getFromDatabase(
            uri: String,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null
    ) {
        GetAsyncTask(getDao(database), Response.Listener {
            if(it == null) {
                Log.i(TAG, "Didn't got $uri from database.")
                getFromUri(uri, onSuccess, onError)
            } else {
                Log.i(TAG, "Got user $uri from database: ${it.hashCode()}")
                onSuccess.onResponse(it)
            }
        }).execute(uri)
    }



    /**
     * Inserts object inside the database.
     *
     * @param obj: Object to insert inside the database
     */
    fun insertInDatabase(obj : T) {
        Log.i(TAG, "Inserting object: ${obj?.hashCode()} in database.")
        InsertAsyncTask(getDao(database)).execute(obj)
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

    open fun put(
            uri: String,
            json: JSONObject,
            onSuccess: Response.Listener<T>,
            onError: Response.ErrorListener? = null
    ):  Request<JSONObject>? =
        queue.add(JsonObjectRequest(
                Request.Method.PUT,
                uri,
                json,
                Response.Listener {
                    Log.i(TAG, "Successfully sent put request to $uri.")
                    onSuccess.onResponse(parse(json))
                },
                Response.ErrorListener {
                    Log.e(TAG, "Failed to send put request to $uri. Status code: ${it.networkResponse.statusCode}")
                    onError?.run { onErrorResponse(it) }
                }
        ))


    /**
     * Parses [JSONObject] into a [T] object.
     *
     * @param json: the json object to be parsed;
     * @return instance defined object.
     */
    protected abstract fun parse(json: JSONObject) : T

    protected abstract fun getDao(database: MobileDatabase) : V

}