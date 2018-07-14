package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.util.LruCache
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ClientDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject

/**
 * Client access class, will get [Client] object with the
 * information on the current client.
 */
class ClientAccess
    : AbstractAccess<Client, ClientDao>() {
    companion object {
        private val mInstance by lazy { ClientAccess() }
        fun getInstance() = mInstance
    }

    private val cache by lazy { LruCache<String, Value<Client>>(1)}

    override fun getSingleCache() = cache

    override fun getDao(database: MobileDatabase) = database.clientDao()

    override fun parse(json: JSONObject) = Client.parse(json)

}