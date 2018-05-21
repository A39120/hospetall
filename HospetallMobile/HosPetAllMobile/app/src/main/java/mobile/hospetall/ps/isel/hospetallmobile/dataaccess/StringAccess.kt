package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.RequestQueue
import org.json.JSONObject

class StringAccess(queue: RequestQueue) : AbstractAccess<String>(queue) {

    override fun parse(json: JSONObject) = json.getString("name")

}