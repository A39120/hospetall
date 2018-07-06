package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ClientDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject

/**
 * Client access class, will get [Client] object with the
 * information on the current client.
 */
class ClientAccess(application: HospetallApplication)
    : AbstractAccess<Client, ClientDao>(application) {

    override fun getDao(database: MobileDatabase) = database.clientDao()

    override fun parse(json: JSONObject) = Client.parse(json)

}