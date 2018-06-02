package mobile.hospetall.ps.isel.hospetallmobile.models

import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.getLink
import mobile.hospetall.ps.isel.hospetallmobile.getLinks
import org.json.JSONObject
import java.sql.Date

/**
 * Procedure open class that will share the same attributes
 * as Consultation and Treatment.This information will come
 * from the api.
 */
open class Procedure(
        val id: Int,
        val date: String,
        val caseHistory: String?,
        val diagnosis: String?,
        val treatment: String?,
        val observations: String?,
        val petUri: String?
)

/**
 * Treatment that extends from Procedure, extra field is the
 *  nurse responsible for the treatment.
 */
class Treatment(
        id: Int,
        date: String,
        caseHistory: String,
        diagnosis: String?,
        treatment: String?,
        observations: String?,
        val nurseUri: String?,
        petUri: String?
) : Procedure(id, date, caseHistory, diagnosis, treatment, observations, petUri), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeString(caseHistory)
        parcel.writeString(diagnosis)
        parcel.writeString(treatment)
        parcel.writeString(observations)
        parcel.writeString(nurseUri)
        parcel.writeString(petUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Treatment> {
        override fun createFromParcel(parcel: Parcel): Treatment {
            return Treatment(parcel)
        }

        override fun newArray(size: Int): Array<Treatment?> {
            return arrayOfNulls(size)
        }
    }
}

fun parseTreatment(json: JSONObject): Treatment{
    val links = json.getLinks()

    return Treatment(
            json.getInt("id"),
            json.getString("date"),
            json.getString("case_history"),
            json.getString("diagnosis"),
            json.getString("treatment"),
            json.getString("observations"),
            links.getLink("nurse"),
            links.getLink("pets")
    )
}

/**
 * Consultation that extends from Procedure, contains extra
 * information related to the Procedure.
 */
class Consultation(
        id: Int,
        date: String,
        caseHistory: String,
        diagnosis: String?,
        treatment: String?,
        observations: String?,
        val weight: Double?,
        val heartRhythm: Double?,
        val temperature: Double?,
        val vetUri: String?,
        petUri: String?
) : Procedure(id, date, caseHistory, diagnosis, treatment, observations, petUri), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date)
        parcel.writeString(caseHistory)
        parcel.writeString(diagnosis)
        parcel.writeString(treatment)
        parcel.writeString(observations)
        parcel.writeValue(weight)
        parcel.writeValue(heartRhythm)
        parcel.writeValue(temperature)
        parcel.writeString(vetUri)
        parcel.writeString(petUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Consultation> {
        override fun createFromParcel(parcel: Parcel): Consultation {
            return Consultation(parcel)
        }

        override fun newArray(size: Int): Array<Consultation?> {
            return arrayOfNulls(size)
        }
    }
}

/**
 * @param json: HAL json object
 */
fun parseConsultation(consultation: JSONObject): Consultation{
    val links = consultation.getLinks()

    return Consultation(
            consultation.getInt("id"),
            consultation.getString("date"),
            consultation.getString("caseHistory"),
            consultation.getString("diagnosis"),
            consultation.getString("treatment"),
            consultation.getString("observations"),
            consultation.getDouble("weight"),
            consultation.getDouble("heartRhythm"),
            consultation.getDouble("temperature"),
            links.getLink("veterinarian"),
            links.getLink("pets")
    )
}