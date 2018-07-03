package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base
import mobile.hospetall.ps.isel.hospetallmobile.utils.AsyncTaskImpl
import org.json.JSONObject

abstract class AbstractListAccess<T: Base, V : CollectionDao<T>>(
        private val property: String,
        private val dao: V
) : AbstractAccess<T, V>(dao) {
    companion object {
        const val TAG = "HPA/Access/List"
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
        AsyncTask.execute{
            Log.i(TAG, "Inserting collection $uri in database.")
            database.beginTransaction()
            database.ListDao().insertAll(list.map{ ListEntity(uri, it.uri) })
            dao.insertAll(list)
            database.endTransaction()
        }
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
                                           onError: Response.ErrorListener? = null){

        AsyncTaskImpl(
                uri,
                {
                    val list= database.ListDao().get(it)
                    list.map { dao.get(it.singleEntityUri) }
                },
                {
                    if(it != null && !it.isEmpty()) {
                        Log.i(TAG, "Got collection from database: $uri")
                        onSuccess.onResponse(it)
                    } else
                        getCollectionFromUri(uri, onSuccess, onError)
                }).execute()
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
                            Log.i(TAG, "Got json list data from $uri")
                            val jsonArr = it.optJSONObject(embedded)?.optJSONArray(property)
                            if(jsonArr != null) {
                                val list =
                                        List(jsonArr.length(), { jsonArr.get(it) as JSONObject })
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

        val listDao = database.ListDao()
        val list = dao.getAll()

        listDao.delete(uri)

        val delete = list.filter {
            val obj = it
            return@filter !newList.contains(obj) || newList.find { obj.id == it.id } != null
        }
        delete.forEach{ dao.deleteById(it.uri) }

        val toInsertOrUpdate = newList.filter(list::contains)
        toInsertOrUpdate.forEach {
            listDao.insert(ListEntity(uri, it.uri))
            dao.insertOrUpdate(it)
        }

        database.endTransaction()
    }

}