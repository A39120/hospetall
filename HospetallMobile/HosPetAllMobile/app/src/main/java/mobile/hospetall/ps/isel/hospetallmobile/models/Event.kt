package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import androidx.work.Data
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data class for an event related to a certain [Pet].
 * @property id: Normal id, since this isn't dependent on the
 * api, it will auto generate one id;
 * @property title: Summary of the event;
 * @property message: Message that contains the info on the
 * event;
 * @property pet: ID of the [Pet];
 * @property period: Long that contains the repetition period
 * if -1, the event will not be repeated;
 * @property timedate: Start date of the event;
 * @property appointed: Tells if the even has an appointment
 * in the api or not;
 * @property type: Integer that references the type of event:
 *      0: User Event
 *      1: Product Event
 *      2: Consultation
 *      3: Treatment
 *  The type is used to make appointments for consultation
 *  or treatment.
 */
@Entity(tableName = Event.TABLE_NAME)
data class Event(
        @ColumnInfo(name=DatabaseColumns.ID)
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0,
        @ColumnInfo(name = TITLE)
        val title: String,
        @ColumnInfo(name= MESSAGE)
        val message: String?,
        @ColumnInfo(name= PET_ID)
        val pet: Int?,
        @ColumnInfo(name= PERIOD)
        val period: Int = -1,
        @ColumnInfo(name=PERIOD_UNIT)
        val periodUnit : Int = 0,
        @ColumnInfo(name=TIME)
        val timedate: Long,
        @ColumnInfo(name=APPOINTED)
        val appointed: Boolean = false,
        @ColumnInfo(name=TYPE)
        val type: Int = EventType.USER
) : Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readInt(),
                parcel.readInt(),
                parcel.readLong(),
                parcel.readByte() != 0.toByte(),
                parcel.readInt()
        )


        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.apply {
                writeInt(id)
                writeString(title)
                writeString(message)
                pet?.apply{writeInt(this)}
                writeInt(period)
                writeInt(periodUnit)
                writeLong(timedate)
                if(appointed)
                    writeByte(1)
                else
                    writeByte(0)
                writeInt(type)
            }
        }

        override fun describeContents(): Int {
            return -1
        }

        companion object CREATOR : Parcelable.Creator<Event> {

            object EventType {
                const val USER = 0
                const val PRODUCT  = 1
                const val CONSULTATION = 2
                const val TREATMENT = 3

                fun getType(value : Int) = when(value){
                    0 -> "User event"
                    1 -> "Product"
                    2 -> "Consultation"
                    3 -> "Treatment"
                    else -> null
                }
            }

            const val PERIOD_UNIT = "period_unit"
            const val TITLE = "title"
            const val PET_ID = "pet_id"
            const val PERIOD = "period"
            const val MESSAGE = "message"
            const val TIME = "time"
            const val APPOINTED = "appointed"
            const val TYPE = "type"
            const val TABLE_NAME= "event"

            override fun createFromParcel(parcel: Parcel): Event {
                    return Event(parcel)
            }

            override fun newArray(size: Int): Array<Event?> {
                    return arrayOfNulls(size)
           }

            /**
             * Method needed for data to be  shared between
             * Work/Notification.
             */
            fun toData(event: Event, id: Int)=
                Data.Builder().apply{
                    putInt(DatabaseColumns.ID, id)
                    putString(TITLE, event.title)
                    putString(MESSAGE, event.message)
                    putInt(PERIOD, event.period)
                    putInt(PERIOD_UNIT, event.periodUnit)
                }.build()
        }
}

