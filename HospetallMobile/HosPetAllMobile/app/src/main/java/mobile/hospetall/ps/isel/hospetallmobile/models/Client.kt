package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLink
import mobile.hospetall.ps.isel.hospetallmobile.utils.getLinks
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.LinkValues
import org.json.JSONObject

@Entity(tableName = Client.TABLE_NAME)
class Client(
        uri : String,
        id: Int,

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
        val other: String? = null,

        @ColumnInfo(name= PETS_URI)
        val petsUrl: String? = null
) : Parcelable, Base(uri, id) {
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
        parcel.writeString(uri)
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

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.apply {
            put(DatabaseColumns.URI, uri)
            put(DatabaseColumns.ID, id)
            put(FAMILY_NAME, familyName)
            put(GIVEN_NAME, givenName)
            put(EMAIL, email)
            put(TELEPHONE, telephone)
            put(ADDRESS, address)
            put(POSTAL_CODE, postalCode)
            put(NIF, nif)
        }
        return json
    }

    companion object CREATOR : Parcelable.Creator<Client> {
        override fun createFromParcel(parcel: Parcel): Client {
            return Client(parcel)
        }

        override fun newArray(size: Int): Array<Client?> {
            return arrayOfNulls(size)
        }

        const val TABLE_NAME = "client"
        const val FAMILY_NAME = "familyName"
        const val GIVEN_NAME = "givenName"
        const val EMAIL = "email"
        const val TELEPHONE = "telephone"
        const val ADDRESS = "address"
        const val POSTAL_CODE = "postalCode"
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

