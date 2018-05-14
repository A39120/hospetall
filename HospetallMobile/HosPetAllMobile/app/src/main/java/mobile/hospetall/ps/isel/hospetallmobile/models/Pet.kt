package mobile.hospetall.ps.isel.hospetallmobile.models

import org.json.JSONObject
import java.sql.Date

data class Pet(
        val id: Int,
        val name: String,
        val species: String?,
        val race: String?,
        val birthdate: Date?,
        val chipNumber: Int,
        val licenceNumber: Int,
        val consultations: Array<Consultation>?,
        val treatments: Array<Treatment>?
)

fun parsePet(json : JSONObject) : Pet{
    val links = json.getJSONObject("_links")
    val pet = json.getJSONObject("pet")

    val id = pet.getInt("id")
    val name = pet.getString("name")
    val birthdate = pet.getString("birthdate")
    val chipNumber = pet.getInt("chip_number")
    val licenceNumber = pet.getInt("license_number")
    //val species =
    //val races =

    val consultationsUri = links.getJSONObject("consultations").getString("href")
    val treatmentUri = links.getJSONObject("treatments").getString("href")

    return Pet(id, name, null, null, Date.valueOf(birthdate), chipNumber, licenceNumber)

}
