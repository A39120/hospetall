package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import com.android.volley.RequestQueue
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import org.json.JSONObject

class ConsultationAccess(queue: RequestQueue,
                         private val database: MobileDatabase)
    : AbstractAccess<Consultation>(queue) {

    override fun getFromDatabase(uri: String): Consultation? =
        database.consultationDao.get(uri)


    /**
     * Inserts a [Consultation] object into the database
     * @param obj: [Consultation] object, with info of the new pet.
     */
    override fun insertInDatabase(obj: Consultation) {
        AsyncTask.execute {
            database.beginTransaction()
            database.consultationDao.insertOrUpdate(obj)
            database.endTransaction()
        }

    }

    /**
     * Inserts a collection of [Consultation] in the database.
     * @param list: list of Consultation objects
     */
    override fun insertCollectionInDatabase(list: List<Consultation>) {
        AsyncTask.execute{
            database.beginTransaction()
            list.forEach(database.consultationDao::insertOrUpdate)
            database.endTransaction()
        }
    }

    override fun parse(json: JSONObject): Consultation = Consultation.parse(json)

    fun getList(uri: String,  onSuccess: Response.Listener<List<Consultation>>, onError: Response.ErrorListener) {
        getCollectionFromUri(uri, "consultationList", onSuccess, onError)
    }
}