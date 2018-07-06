package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLink
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLinks
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.LinkValues
import org.json.JSONObject

/**
 * Consultation that extends from Procedure, contains extra
 * information related to the Procedure.
 */
@Entity( tableName = Consultation.TABLE_NAME )
class Consultation(
        uri: String,
        id: Int,
        date: String,
        caseHistory: String,
        diagnosis: String?,
        treatment: String?,
        observations: String?,

        @ColumnInfo(name=WEIGHT)
        val weight: Double?,

        @ColumnInfo(name=HEART_RHYTHM)
        val heartRhythm: Double?,

        @ColumnInfo(name=TEMPERATURE)
        val temperature: Double?,

        @ColumnInfo(name= VET_URI)
        val vetUri: String?,

        petUri: String
) : Procedure(uri, id, date, caseHistory, diagnosis, treatment, observations, petUri), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
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

    /**
     * Writes information to a parcel for easy communication
     * between activities.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(uri)
            writeInt(id)
            writeString(date)
            writeString(caseHistory)
            writeString(diagnosis)
            writeString(treatment)
            writeString(observations)
            writeValue(weight)
            writeValue(heartRhythm)
            writeValue(temperature)
            writeString(vetUri)
            writeString(petUri)
        }
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

        /**
         * Constants that specify the name of a column on the
         * database or key in the JSON format.
         */
        const val TABLE_NAME = "consultation"
        const val WEIGHT = "weight"
        const val HEART_RHYTHM = "heartRhythm"
        const val TEMPERATURE= "temperature"
        const val VET_URI = "vet_uri"

        /**
         * Key used to get the veterinarian uri on the json object.
         */
        private const val VETERINARIAN = "veterinarian"


        /**
         * Parses [JSONObject] into [Consultation]
         */
        fun parse(consultation: JSONObject): Consultation{
            val links = consultation.getLinks()

            return Consultation(
                    links.getLink(LinkValues.SELF)!!,
                    consultation.getInt(DatabaseColumns.ID),
                    consultation.getString(DATE),
                    consultation.getString(CASE_HISTORY),
                    consultation.getString(DIAGNOSIS),
                    consultation.getString(TREATMENT),
                    consultation.getString(OBSERVATIONS),
                    consultation.getDouble(WEIGHT),
                    consultation.getDouble(HEART_RHYTHM),
                    consultation.getDouble(TEMPERATURE),
                    links.getLink(VETERINARIAN),
                    links.getLink(PETS)!!
            )
        }
    }
}
