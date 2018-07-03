package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ConsultationDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import org.json.JSONObject

class ConsultationAccess
    : AbstractListAccess<Consultation, ConsultationDao>("consultationList", MobileDatabase.getInstance().ConsultationDao()) {

    override fun parse(json: JSONObject): Consultation = Consultation.parse(json)

}