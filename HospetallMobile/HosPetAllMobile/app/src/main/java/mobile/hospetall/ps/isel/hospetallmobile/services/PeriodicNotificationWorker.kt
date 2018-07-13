package mobile.hospetall.ps.isel.hospetallmobile.services

import android.util.Log
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.notifications.NotificationReceiver
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import java.util.*

class PeriodicNotificationWorker : Worker() {
    companion object {
        private const val NAME = "PERIODIC_NOTIFICATION"
        private const val TAG = "HPA/WORK/NOTIF_PERIOD"
        private const val EVENT_ID = "event_id"

        fun scheduleNotification(event: Event) {
            val period = event.period
            val periodUnit = event.periodUnit

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = event.timedate

            val number = DateUtils.getPeriod(periodUnit, period)
            val unit = DateUtils.getPeriodUnit(periodUnit)

            val data = Data.Builder()
                    .putInt(EVENT_ID, event.id)
                    .build()

            Log.i(TAG, "Setting up repetitive notification work: $number $unit")
            val periodWork =
                    androidx.work.PeriodicWorkRequest.Builder(PeriodicNotificationWorker::class.java, number, unit)
                            .setInputData(data)
                            .addTag(NAME)
                            .build()
            WorkManager.getInstance()?.enqueue(periodWork)
        }
    }

    private val scheduleAccess by lazy { ScheduleAccess() }

    override fun doWork(): Result {
        Log.i(TAG, "Creating a periodic notification.")

        val id = inputData.getInt(EVENT_ID, -1)
        if(id < 0)
            return Result.FAILURE

        val live = scheduleAccess.get(id).value
        if(live != null) {
            Log.i(TAG, "Got event ${live.id}")
            NotificationReceiver.start(applicationContext, live)
        } else {
            Log.e(TAG, "Error getting ")
        }

        return Result.SUCCESS
    }

}
