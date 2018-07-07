package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.TreatmentDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import org.json.JSONObject

class TreatmentAccess
    : AbstractListAccess<Treatment, TreatmentDao>("treatmentList") {

    override fun getDao(database: MobileDatabase) = database.treatmentDao()

    override fun parse(json: JSONObject) = Treatment.parse(json)

}
