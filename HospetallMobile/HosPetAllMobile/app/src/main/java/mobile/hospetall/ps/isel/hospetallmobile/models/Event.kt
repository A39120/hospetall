package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

data class Event(
        @ColumnInfo(name=DatabaseColumns.ID)
        @PrimaryKey(autoGenerate = true)
        val id : Int,
        @ColumnInfo(name = TITLE)
        val title: String,
        @ColumnInfo(name= MESSAGE)
        val message: String?,
        @ColumnInfo(name= PET_ID)
        val pet: Int?,
        @ColumnInfo(name= PERIODIC)
        val periodic: Boolean = false,
        @ColumnInfo(name=TIME)
        val time: Long
) : Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readByte() != 0.toByte(),
                parcel.readLong()) {
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.apply {
                writeInt(id)
                writeString(title)
                writeString(message)
                pet?.apply{writeInt(pet)}
                if(periodic)
                    writeByte(1)
                else
                    writeByte(0)
                writeLong(time)
            }
        }

        override fun describeContents(): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        companion object CREATOR : Parcelable.Creator<Event> {

                const val TITLE = "title"
                const val PET_ID = "pet_id"
                const val PERIODIC = "periodic"
                const val MESSAGE = "message"
                const val TIME = "time"

                override fun createFromParcel(parcel: Parcel): Event {
                        return Event(parcel)
                }

                override fun newArray(size: Int): Array<Event?> {
                        return arrayOfNulls(size)
                }
        }
}

