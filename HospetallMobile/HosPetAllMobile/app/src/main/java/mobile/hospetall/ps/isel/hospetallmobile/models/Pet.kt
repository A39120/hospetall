package mobile.hospetall.ps.isel.hospetallmobile.models

import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject
import java.sql.Date

data class Pet(
        val id: Int,
        val name: String,
        val species: String?,
        val race: String?,
        val birthDate: String?,
        val chipNumber: Int,
        val licenceNumber: Int,
        val consultationUri: String?,
        val treatmentUri: String?
)

fun parsePet(pet: JSONObject) : Pet{
    val links = pet.getLinks()

    val id = pet.getInt("id")
    val name = pet.getString("name")
    val birthDate = pet.optString("birthdate")
    val chipNumber = pet.optInt("chip_number")
    val licenceNumber = pet.optInt("license_number")
    //val species =
    //val races =

    val consultationsUri : String? = links.optJSONObject("consultations")?.getString("href")
    val treatmentUri : String? = links.optJSONObject("treatments")?.getString("href")

    return Pet(id, name, null, null, birthDate, chipNumber, licenceNumber, consultationsUri, treatmentUri)
}
