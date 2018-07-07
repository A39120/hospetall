package mobile.hospetall.ps.isel.hospetallmobile.utils


import android.os.Build
import java.sql.Date
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.time.Instant
import java.util.concurrent.TimeUnit

object DateUtils {

    fun getPeriodUnit(unit : Int) : TimeUnit =
            when(unit) {
               0 -> TimeUnit.MINUTES
               1 -> TimeUnit.HOURS
               else -> TimeUnit.DAYS
    }

    fun getPeriod(unit: Int, period: Int) : Long =
        when(unit) {
            3 -> period * 7
            4 -> period * 30
            5 -> period * 365
            else -> period
        }.toLong()


    fun getCurrentDateString() =
            getDateString(getCurrentDateMillis())

    fun getCurrentTimeString() =
            getTimeString(getCurrentDateMillis())

    fun getDateString(date : Long): String {
        val dateInstance = getDateInstance()
        return dateInstance.format(Date(date))
    }

    fun getTimeString(time: Long) : String {
        return getTimeInstance().format(Date(time))
    }

    fun getCurrentDateMillis() : Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Date.from(Instant.now()).time
        else
            System.currentTimeMillis()

}