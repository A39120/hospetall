package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.RequestQueue
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.models.parseTreatment
import org.json.JSONObject

class TreatmentAccess(queue: RequestQueue) : AbstractAccess<Treatment>(queue) {
    override fun parse(json: JSONObject) = parseTreatment(json)
}