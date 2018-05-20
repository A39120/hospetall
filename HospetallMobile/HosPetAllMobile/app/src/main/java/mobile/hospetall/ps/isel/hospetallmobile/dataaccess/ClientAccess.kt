package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.RequestQueue
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.models.parseClient
import org.json.JSONObject

class ClientAccess(queue: RequestQueue) : AbstractAccess<Client>(queue) {

    override fun parse(json: JSONObject) = parseClient(json)



}