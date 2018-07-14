package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import android.util.LruCache
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ListDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base
import org.json.JSONObject

abstract class AbstractListAccess<T: Base, V : CollectionDao<T>>(
        private val property: String
) : AbstractAccess<T, V>() {
    companion object {
        const val TAG = "HPA/Access/List"

        /**
         * Async Task that has the purpose of inserting a collection
         * inside the Room Database
         */
        private class InsertListAsyncTask<T: Base, V: CollectionDao<T>>(
                private val listDao : ListDao,
                private val uri: String,
                private val dao: V
        ) : AsyncTask<List<T>, Unit, Unit>() {
            override fun doInBackground(vararg params: List<T>) {
                listDao.insertAll(params[0].map { ListEntity(uri, it.uri) })
                dao.insertAll(params[0])
            }
        }
    }

    private val listDao = database.listDao()

    /**
     * Get function to obtain an multiple objects represented
     * with an uri, and pass it to a callback if there is an
     * answer.
     *
     * @param uri: the chosen id for most objects;
     */
    fun getList(uri: String) : LiveData<List<T>> {
        val cachedList = getCollectionCache().get(uri)
        return if(cachedList != null && cachedList.timeOfInsertion - System.currentTimeMillis() < timeout)
            cachedList.value
        else {
            getCollectionCache().remove(uri)
            updateCollectionFromNetwork(uri)
            getCollectionFromDatabase(uri)
        }
    }



    /**
     * Inserts collection in database.
     *
     * @param uri: collection id.
     * @param list: collection to be added.
     */
    fun insertCollectionInDatabase(uri: String, list : List<T>){
        Log.i(TAG, "Inserting collection $uri in database.")
        InsertListAsyncTask(listDao, uri, getDao(database)).execute(list)
    }

    /**
     * Obtains the collection identified by an uri from the
     * database.
     *
     * @param uri: The id of the collection
     */
    private fun getCollectionFromDatabase(uri: String) : LiveData<List<T>>{
        val data = getDao(database).getList(uri)
        getCollectionCache().put(uri, Value(data, System.currentTimeMillis()))
        return data
    }

    /**
     * Gets a list of objects directly from the Web API
     * represented with the [uri].
     */
    fun updateCollectionFromNetwork(uri: String) {
        val embedded = "_embedded"
        queue.add(
                JsonObjectRequest(
                        uri,
                        null,
                        Response.Listener {
                            Log.i(TAG, "Got json list data from network $uri")
                            val jsonArr = it.optJSONObject(embedded)?.optJSONArray(property)
                            jsonArr?.apply {
                                val list =
                                        List(this.length(), { this.get(it) as JSONObject })
                                                .map { parse(it) }
                                insertCollectionInDatabase(uri, list)
                            }
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Failed to send get request for list from $uri")
                        }
                )
        )
    }

    protected abstract fun getCollectionCache() : LruCache<String, Value<List<T>>>

}