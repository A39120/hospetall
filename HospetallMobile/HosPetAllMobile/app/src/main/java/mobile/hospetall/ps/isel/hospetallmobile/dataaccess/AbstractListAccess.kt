package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ListDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base
import org.json.JSONObject

abstract class AbstractListAccess<T: Base, V : CollectionDao<T>>(
        application: HospetallApplication,
        private val property: String
) : AbstractAccess<T, V>(application) {
    companion object {
        const val TAG = "HPA/Access/List"

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

        private class GetListAsyncTask<T,V: CollectionDao<T>>(
                private val dao: V,
                private val listener : Response.Listener<List<T>>
        ) : AsyncTask<String, Unit, List<T>>() {

            override fun doInBackground(vararg params: String?) : List<T>? =
                params[0]?.let {
                    dao.getList(it)
                }

            override fun onPostExecute(result: List<T>?) {
                super.onPostExecute(result)
                listener.onResponse(result)
            }

        }
    }

    private val listDao = database.listDao()
    protected val dao = getDao(database)

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
                onSuccess: Response.Listener<List<T>>,
                onError: Response.ErrorListener? = null) {

        getCollectionFromDatabase( uri, onSuccess, onError)
    }


    /**
     * Inserts collection in database.
     *
     * @param uri: collection id.
     * @param list: collection to be added.
     */
    private fun insertCollectionInDatabase(uri: String, list : List<T>){
        Log.i(TAG, "Inserting collection $uri in database.")
        InsertListAsyncTask(listDao, uri, dao).execute(list)
    }

    /**
     * Obtains the collection identified by an uri from the
     * database.
     *
     * @param uri: The id of the collection
     * @param onSuccess: In case of success this callback
     * will be called with the result
     */
    private fun getCollectionFromDatabase(uri: String,
                                           onSuccess: Response.Listener<List<T>>,
                                           onError: Response.ErrorListener? = null) {
        GetListAsyncTask(
                dao,
                Response.Listener {
                    if (!it.isEmpty()) {
                        Log.i(TAG, "Got collection $uri from database.")
                        onSuccess.onResponse(it)
                    } else {
                        Log.i(TAG, "Didn't got collection $uri from database.")
                        getCollectionFromUri(uri, onSuccess, onError)
                    }
                }
        ).execute(uri)
    }



    /**
     * Gets a list of objects directly from the Web API
     * represented with the [uri].
     */
    private fun getCollectionFromUri(
            uri: String,
            onSuccess: Response.Listener<List<T>>,
            onError: Response.ErrorListener?
    ) {
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

    fun update(uri: String){
        getCollectionFromUri(
                uri,
                Response.Listener {
                    Log.i(TAG, "Updating list from $uri.")
                    updateTable(uri, it)
                },
                Response.ErrorListener {
                    Log.e(TAG, "Failed to update table.")
                }
        )
    }

    /**
     * Updates consultation table by comparing current table with
     * the previous one.
     *
     * @param newList: The new consultation list
     */
    private fun updateTable(uri: String, newList: List<T>) {
            database.beginTransaction()

            val list = dao.getAll()
            listDao.delete(uri)

            val delete = list.filter {
                val obj = it
                return@filter !newList.contains(obj) || newList.find { obj.id == it.id } != null
            }
            delete.forEach { dao.deleteById(it.uri) }

            val toInsertOrUpdate = newList.filter(list::contains)
            toInsertOrUpdate.forEach {
                listDao.insert(ListEntity(uri, it.uri))
                dao.insertOrUpdate(it)
            }

            database.endTransaction()
    }

}