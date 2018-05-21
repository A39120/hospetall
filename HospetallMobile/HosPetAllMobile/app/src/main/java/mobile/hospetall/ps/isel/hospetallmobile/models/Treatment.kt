package mobile.hospetall.ps.isel.hospetallmobile.models

import mobile.hospetall.ps.isel.hospetallmobile.getLink
import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject

data class Treatment(
        val id: Int,
        val caseHistory: String,
        val diagnosis: String?,
        val treatment: String?,
        val observations: String?,
        val nurseUri: String?,
        val petUri: String?
)

fun parseTreatment(json: JSONObject): Treatment{
    val links = json.getLinks()

    return Treatment(
            json.getInt("id"),
            json.getString("case_history"),
            json.getString("diagnosis"),
            json.getString("treatment"),
            json.getString("observations"),
            links.getLink("nurse"),
            links.getLink("pets")
    )
}