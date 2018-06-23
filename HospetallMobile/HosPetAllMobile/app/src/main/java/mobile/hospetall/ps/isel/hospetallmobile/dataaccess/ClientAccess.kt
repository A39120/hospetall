package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import com.android.volley.RequestQueue
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject

/**
 * Client access class, will get [Client] object with the
 * information on the current client.
 */
class ClientAccess(queue: RequestQueue,
                   private val database: MobileDatabase
) : AbstractAccess<Client>(queue) {

    override fun getFromDatabase(uri: String): Client? =
            database.clientDao.get(uri)


    /**
     * Inserts a [Client] object into the database
     * @param obj: [Client] object;
     */
    override fun insertInDatabase(obj: Client) {

        AsyncTask.execute {
            database.beginTransaction()
            database.clientDao.insertOrUpdate(obj)
            database.endTransaction()
        }
    }

    /**
     * There should not be list of [Client] therefore this method
     * will throw exception.
     */
    override fun insertCollectionInDatabase(list: List<Client>) {
        throw IllegalAccessError("Can't insert multiple clients into the client table.")
    }

    override fun parse(json: JSONObject) = Client.parse(json)

}