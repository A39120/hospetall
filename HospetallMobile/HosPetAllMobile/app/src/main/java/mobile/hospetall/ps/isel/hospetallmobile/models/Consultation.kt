package mobile.hospetall.ps.isel.hospetallmobile.models

import mobile.hospetall.ps.isel.hospetallmobile.getLink
import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject

data class Consultation(
    val id: Int,
    val caseHistory: String,
    val diagnosis: String?,
    val treatment: String?,
    val observations: String?,
    val weight: Double?,
    val heartRhythm: Double?,
    val temperature: Double?,
    val vetUri: String?,
    val petUri: String?
)

/**
 * @param json: HAL json object
 */
fun parseConsultation(consultation: JSONObject): Consultation{
    val links = consultation.getLinks()

    return Consultation(
            consultation.getInt("id"),
            consultation.getString("case_history"),
            consultation.getString("diagnosis"),
            consultation.getString("treatment"),
            consultation.getString("observations"),
            consultation.getDouble("weight"),
            consultation.getDouble("heart_rhythm"),
            consultation.getDouble("temperature"),
            links.getLink("veterinarian"),
            links.getLink("pets")
    )
}