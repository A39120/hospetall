package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ClientDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject

/**
 * Client access class, will get [Client] object with the
 * information on the current client.
 */
class ClientAccess
    : AbstractAccess<Client, ClientDao>(MobileDatabase.getInstance().ClientDao()) {


    override fun parse(json: JSONObject) = Client.parse(json)

}