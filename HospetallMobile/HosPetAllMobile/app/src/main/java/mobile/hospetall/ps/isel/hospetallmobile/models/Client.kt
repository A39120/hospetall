package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.LinkValues
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLink
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLinks
import org.json.JSONObject

@Entity(tableName = Client.TABLE_NAME)
data class Client(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = DatabaseColumns.URI)
        val uri : String,

        //@NotNull val etag: String,
        @ColumnInfo(name= DatabaseColumns.ID)
        val id: Int,

        @ColumnInfo(name= FAMILY_NAME)
        val familyName: String,

        @ColumnInfo(name= GIVEN_NAME)
        val givenName: String,

        @ColumnInfo(name= EMAIL)
        val email: String,

        @ColumnInfo(name= TELEPHONE)
        val telephone: String,

        @ColumnInfo(name= ADDRESS)
        val address: String?,

        @ColumnInfo(name= POSTAL_CODE)
        val postalCode: String?,

        @ColumnInfo(name= NIF)
        val nif: Int?,

        @ColumnInfo(name= OTHER)
        val other: String?,

        @ColumnInfo(name= PETS_URI)
        val petsUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(familyName)
        parcel.writeString(givenName)
        parcel.writeString(email)
        parcel.writeString(telephone)
        parcel.writeString(address)
        parcel.writeString(postalCode)
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

        const val TABLE_NAME = "client"
        const val FAMILY_NAME = "family_name"
        const val GIVEN_NAME = "given_name"
        const val EMAIL = "email"
        const val TELEPHONE = "telephone"
        const val ADDRESS = "address"
        const val POSTAL_CODE = "postal_code"
        const val NIF = "nif"
        const val OTHER = "other"
        const val PETS_URI = "pets_uri"

        fun parse(clientJson: JSONObject) : Client {
            val links = clientJson.getLinks()

            return Client(
                    links.getLink(LinkValues.SELF)!!,
                    clientJson.getInt(DatabaseColumns.ID),
                    clientJson.getString(FAMILY_NAME),
                    clientJson.getString(GIVEN_NAME),
                    clientJson.getString(EMAIL),
                    clientJson.getString(TELEPHONE),
                    clientJson.getString(ADDRESS),
                    clientJson.getString(POSTAL_CODE),
                    clientJson.getInt(NIF),
                    clientJson.getString(OTHER),
                    links.getLink("pets")!!
            )

        }
    }
}

