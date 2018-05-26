package mobile.hospetall.ps.isel.hospetallmobile.models

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(species)
        parcel.writeString(race)
        parcel.writeString(birthDate)
        parcel.writeInt(chipNumber)
        parcel.writeInt(licenceNumber)
        parcel.writeString(consultationUri)
        parcel.writeString(treatmentUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }
}

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
