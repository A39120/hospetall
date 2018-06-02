package mobile.hospetall.ps.isel.hospetallmobile.models

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(familyName)
        parcel.writeString(givenName)
        parcel.writeString(email)
        parcel.writeString(telephone)
        parcel.writeString(address)
        parcel.writeString(postalCode)
        parcel.writeString(telephoneAlternative)
        parcel.writeValue(nif)
        parcel.writeString(other)
        parcel.writeString(petsUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Client> {
        override fun createFromParcel(parcel: Parcel): Client {
            return Client(parcel)
        }

        override fun newArray(size: Int): Array<Client?> {
            return arrayOfNulls(size)
        }
    }
}

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