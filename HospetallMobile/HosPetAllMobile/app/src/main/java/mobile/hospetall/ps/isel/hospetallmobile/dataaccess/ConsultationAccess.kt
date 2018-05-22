package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.RequestQueue
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.parseConsultation
import org.json.JSONObject

class ConsultationAccess(queue: RequestQueue)
    : AbstractAccess<Consultation>(queue) {

    override fun parse(json: JSONObject): Consultation = parseConsultation(json)
}