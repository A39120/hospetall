package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.LinkValues
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLinks
import org.jetbrains.annotations.NotNull
import org.json.JSONObject

/**
 * Pet entity, represents one pet. This entity will also have a
 * table of it's own.
 */
@Entity(tableName = Pet.TABLE_NAME)
data class Pet(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name= DatabaseColumns.URI)
        val uri: String,

        //@NotNull
        //@ColumnInfo(name=DatabaseColumns.ETAG)
        //val etag: String,

        @NotNull
        @ColumnInfo(name= DatabaseColumns.ID)
        val id: Int,

        @NotNull
        @ColumnInfo(name=NAME)
        val name: String,

        @ColumnInfo(name= SPECIES)
        val species: String?,

        @ColumnInfo(name= RACE)
        val race: String?,

        @ColumnInfo(name= BIRTH_DATE)
        val birthDate: String?,

        @ColumnInfo(name=CHIP)
        val chipNumber: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            //parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as Int?
            //parcel.readString(),
            //parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeString(uri)
           // writeString(etag)
            writeInt(id)
            writeString(name)
            writeString(species)
            writeString(race)
            writeString(birthDate)
            writeValue(chipNumber)
            //writeString(consultationUri)
            //writeString(treatmentUri)
        }
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

        const val TABLE_NAME = "pet"
        const val NAME = "name"
        const val SPECIES = "species"
        const val RACE = "race"
        const val BIRTH_DATE = "birthdate"
        const val CHIP = "chip_number"
        //const val CONSULTATION_URI = "consultations"
        //const val TREATMENT_URI = "treatments"


        fun parse(pet: JSONObject): Pet {
            val links = pet.getLinks()

            val id = pet.getInt(DatabaseColumns.ID)
            val name = pet.getString(Pet.NAME)
            val birthDate = pet.optString(Pet.BIRTH_DATE)
            val chipNumber = pet.opt(Pet.CHIP) as Int?
            val species = pet.optString(Pet.SPECIES)
            val race = pet.optString(Pet.RACE)

            //val consultationsUri: String? = links.optJSONObject(CONSULTATION_URI)?.getString(LinkValues.HREF)
            //val treatmentUri: String? = links.optJSONObject(TREATMENT_URI)?.getString(LinkValues.HREF)
            val self = links.getJSONObject(LinkValues.SELF).getString(LinkValues.HREF)

            return Pet(self, id, name, species, race, birthDate, chipNumber)//, consultationsUri, treatmentUri)
        }
    }
}