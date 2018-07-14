package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.BaseDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import org.json.JSONObject


/**
 * Abstract class with the purpose of accessing information
 * from multiple sources.
 */
abstract class AbstractAccess<T, V : BaseDao<T>>  {
    companion object {
        const val TAG = "HPA/DA/ABSTRACT"

        class InsertAsyncTask<T, V : BaseDao<T>>(private val dao: V)
            : AsyncTask<T, Unit, Unit>() {
            override fun doInBackground(vararg params: T?) {
                params[0]?.let { dao.insertOrUpdate(it) }
            }
        }
    }

    protected var timeout : Long = 600000 //10 min default timeout
    protected val queue = RequestQueueSingleton.getInstance().requestQueue!!
    protected val database = MobileDatabase.getInstance()

    /**
     * Get function to obtain an object with a certain uri,
     * and pass it to a callback if there is an answer.
     *
     * @param uri: the chosen id for most objects;
     */
    fun get(uri: String): LiveData<T> {
        val cached = getSingleCache().get(uri)
        return if(cached != null && System.currentTimeMillis() - cached.timeOfInsertion < timeout)
            cached.value
        else {
            getSingleCache().remove(uri)
            updateFromNetwork(uri)
            getFromDatabase(uri)
        }
    }


    /**
     * Gets an object directly from the Web API represented
     * with the [uri].
     */
    open fun updateFromNetwork(uri: String)  {
        queue.add(JsonObjectRequest(
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
     * Tries to obtain an object from the database, and
     * inserts it in the cache.
     *
     * @param uri: id of the object stored in the database;
     * @return [LiveData] that will contain the object in the database
     */
    private fun getFromDatabase(uri: String) : LiveData<T> {
        val data =  getDao(database).get(uri)
        getSingleCache().put(uri, Value(data, System.currentTimeMillis()))
        return data
    }

    /**
     * Inserts object inside the database.
     *
     * @param obj: Object to insert inside the database
     */
    private fun insertInDatabase(obj : T) {
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

    fun setCacheTimeout(value : Long) {
        this.timeout = value
    }

    /**
     * Parses [JSONObject] into a [T] object.
     *
     * @param json: the json object to be parsed;
     * @return instance defined object.
     */
    protected abstract fun parse(json: JSONObject) : T

    /**
     * Gets respective Data Access Object to the database
     * present in the classes that extend from this class.
     *
     * @param database: Database that gets the DAO
     */
    protected abstract fun getDao(database: MobileDatabase) : V

    /**
     * Class used for the Last recently used, timed cache.
     */
    class Value<T>(
            val value: LiveData<T>,
            val timeOfInsertion : Long
    )

    /**
     * Returns the cache present in the classes that implement
     * this abstract class.
     */
    protected abstract fun getSingleCache() : LruCache<String, Value<T>>

    /**
     * Individual cache size
     */
    protected var mCacheSize = 100
    fun setCacheSize(value: Int) {
        this.mCacheSize = value
    }
}