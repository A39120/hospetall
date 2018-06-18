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
 * Treatment that extends from Procedure, extra field is the
 *  nurse responsible for the treatment.
 */
@Entity(tableName = Treatment.TABLE_NAME)
class Treatment(
        uri: String,
        id: Int,
        date: String,
        caseHistory: String,
        diagnosis: String?,
        treatment: String?,
        observations: String?,

        @ColumnInfo(name = NURSE_URI)
        val nurseUri: String?,

        petUri: String?
) : Procedure(uri, id, date, caseHistory, diagnosis, treatment, observations, petUri), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(uri)
            writeInt(id)
            writeString(date)
            writeString(caseHistory)
            writeString(diagnosis)
            writeString(treatment)
            writeString(observations)
            writeString(nurseUri)
            writeString(petUri)
        }
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

        /**
         * Constants that specify the name of a column on the
         * database or key in the JSON format.
         */
        const val TABLE_NAME = "treatment"
        const val NURSE_URI = "nurse_uri"

        /**
         * Key that contains the uri for the nurse on the json
         * format.
         */
        private const val NURSE = "nurse"

        /**
         * Parses [JSONObject] into [Treatment].
         */
        fun parse(json: JSONObject): Treatment {
            val links = json.getLinks()

            return Treatment(
                    links.getLink(LinkValues.SELF)!!,
                    json.getInt(DatabaseColumns.ID),
                    json.getString(Procedure.DATE),
                    json.optString(Procedure.CASE_HISTORY),
                    json.optString(Procedure.DIAGNOSIS),
                    json.optString(Procedure.TREATMENT),
                    json.optString(Procedure.OBSERVATIONS),
                    links.getLink(NURSE),
                    links.getLink(PETS)
            )
        }
    }
}