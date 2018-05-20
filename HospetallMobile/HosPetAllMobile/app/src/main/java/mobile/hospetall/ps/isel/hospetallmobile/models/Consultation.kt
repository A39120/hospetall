package mobile.hospetall.ps.isel.hospetallmobile.models

import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject

data class Consultation(

)

/**
 * @param json: HAL json object
 */
fun parseConsultation(json: JSONObject){
    val consultation = json.getJSONObject("consultation")
    val links = json.getLinks()

}