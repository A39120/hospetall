package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import com.android.volley.RequestQueue
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import org.json.JSONObject

class TreatmentAccess(
        queue: RequestQueue,
        private val database: MobileDatabase
) : AbstractAccess<Treatment>(queue) {

    override fun getFromDatabase(uri: String): Treatment? =
        database.treatmentDao.get(uri)


    /**
     * Inserts a [Treatment] object into the database
     * @param obj: [Treatment] object, with info of the new pet.
     */
    override fun insertInDatabase(obj: Treatment) {
        AsyncTask.execute {
            database.beginTransaction()
            database.treatmentDao.insertOrUpdate(obj)
            database.endTransaction()
        }

    }

    /**
     * Inserts a collection of [Treatment] in the database.
     * @param list: list of Treatment objects
     */
    override fun insertCollectionInDatabase(list: List<Treatment>) {
        AsyncTask.execute{
            database.beginTransaction()
            list.forEach(database.treatmentDao::insertOrUpdate)
            database.endTransaction()
        }
    }

    override fun parse(json: JSONObject) = Treatment.parse(json)

    fun getList(uri: String, onSuccess: Response.Listener<List<Treatment>>, onError: Response.ErrorListener) {
        getCollectionFromUri(uri, "treatmentList", onSuccess, onError)
    }
}