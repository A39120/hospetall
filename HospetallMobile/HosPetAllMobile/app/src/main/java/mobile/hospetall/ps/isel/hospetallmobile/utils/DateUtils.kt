package mobile.hospetall.ps.isel.hospetallmobile.utils


import android.os.Build
import java.sql.Date
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.time.Instant

object DateUtils {

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