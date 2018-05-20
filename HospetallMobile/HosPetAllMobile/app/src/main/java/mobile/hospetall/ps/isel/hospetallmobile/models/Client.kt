package mobile.hospetall.ps.isel.hospetallmobile.models

import mobile.hospetall.ps.isel.hospetallmobile.getLink
import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject

data class Client(
        val id: Int,
        val familyName: String,
        val givenName: String,
        val email: String,
        val telephone: String,
        val address: String?,
        val postalCode: String?,
        val telephoneAlternative: String?,
        val nif: Int?,
        val other: String?,
        val petsUrl: String?
)

fun parseClient(clientJson: JSONObject) : Client {
    val id = clientJson.getInt("id")
    val familyName = clientJson.getString("family_name")
    val givenName = clientJson.getString("given_name")
    val email = clientJson.getString("email")
    val telephone = clientJson.getString("telephone")
    val address = clientJson.getString("address")
    val postalCode = clientJson.getString("postal_code")
    val telephoneAlternative = clientJson.getString("telephone_alternative")
    val nif = clientJson.getInt("nif")
    val other = clientJson.getString("other")

    val petsUri = clientJson.getLinks().getLink("pets")

    return Client(id, familyName, givenName, email, telephone, address, postalCode, telephoneAlternative, nif, other, petsUri)

}