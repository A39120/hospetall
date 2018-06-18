package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import com.android.volley.RequestQueue
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import org.json.JSONObject

/**
 * Class for accessing information on a specified request
 * related to Pet.
 */
class PetAccess(
        queue:RequestQueue,
        private val database: MobileDatabase)
    : AbstractAccess<Pet>(queue) {

    fun getList(uri: String, onSuccess: Response.Listener<List<Pet>>, onError: Response.ErrorListener) {
        getCollectionFromUri(uri, "petList", onSuccess, onError)
    }

    /**
     * Parses Json object to [Pet]
     * @param json: JSON object
     * @return [Pet] object
     */
    override fun parse(json: JSONObject) = Pet.parse(json)

    /**
     * Gets pet from internal [MobileDatabase]
     * @param uri: id for pet
     * @return [Pet] object
     */
    override fun getFromDatabase(uri: String) =
            database.petDao.get(uri)


    /**
     * Inserts a [Pet] object into the database
     * @param obj: [Pet] object, with info of the new pet.
     */
    override fun insertInDatabase(obj: Pet) {
        AsyncTask.execute {
                database.beginTransaction()
                database.petDao.insertOrUpdate(obj)
                database.endTransaction()
        }

    }

    /**
     * Inserts a collection of [Pet] in the database.
     * @param list: list of Pet objects
     */
    override fun insertCollectionInDatabase(list: List<Pet>) {
        AsyncTask.execute{
            database.beginTransaction()
            list.forEach(database.petDao::insertOrUpdate)
            database.endTransaction()
        }
    }

}
