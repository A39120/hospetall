package mobile.hospetall.ps.isel.hospetallmobile.services

import android.util.Log
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.notifications.NotificationReceiver
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import java.util.*
import java.util.concurrent.TimeUnit

class OneTimeNotificationWorker : Worker() {
    companion object {
        private const val TAG = "HPA/WORK/NOTIFICATION_1"
        private const val NAME = "one_time_event_notification"

        fun setUpWork(event: Event, id: Int) {
            val data = Event.toData(event, id)
            val calendar = Calendar.getInstance()
            val delay = event.timedate - calendar.timeInMillis

            setUpWork(data, delay, TimeUnit.MILLISECONDS, id)
        }

        fun setUpWork(id: Int, period: Long, unit: Int, title: String, message: String) {
            val data = Data.Builder().apply{
                putInt(DatabaseColumns.ID, id)
                putLong(Event.PERIOD, period)
                putInt(Event.PERIOD_UNIT, unit)
                putString(Event.TITLE, title)
                putString(Event.MESSAGE, message)
            }.build()

            val delay = period
            val delayUnit = DateUtils.getPeriodUnit(unit)
            setUpWork(data, delay, delayUnit, id)
        }

        private fun setUpWork(data: Data, delay: Long, unit: TimeUnit, id: Int){
            Log.i(TAG, "Setting up one time notification work:  start in $delay $unit.")
            val oneTimeWork =
                    androidx.work.OneTimeWorkRequest.Builder(OneTimeNotificationWorker::class.java)
                            .setInitialDelay(delay, unit)
                            .setInputData(data)
                            .addTag(NAME + "$id")
                            .build()

            WorkManager.getInstance()?.enqueue(oneTimeWork)
        }

        fun cancelWork(id: Int){
            Log.i(TAG, "Cancelling work with id: $id")
            WorkManager.getInstance()?.cancelAllWorkByTag(NAME+"$id")
        }
    }

    override fun doWork(): Result {
        Log.i(TAG,"Executing one time work.")

        //Gets data passed
        val eventId = inputData.getInt(DatabaseColumns.ID, -1)
        if(eventId == -1) {
            Log.e(TAG, "Event id could not be extracted from the Worker.")
            return Result.FAILURE
        }

        val period = inputData.getLong(Event.PERIOD, 0)
        val periodUnit = inputData.getInt(Event.PERIOD_UNIT, 0)
        val title = inputData.getString(Event.TITLE, "")?:""
        val message = inputData.getString(Event.MESSAGE, "")

        Log.i(TAG, "Got event with id $eventId, with period $period")
        if(period > 0){
            Log.i(TAG, "Setting up a new notification worker.")
            val unit = DateUtils.getPeriodUnit(periodUnit)
            OneTimeNotificationWorker.setUpWork(inputData, period, unit, eventId)
            //PeriodicNotificationWorker.scheduleNotification(period, periodUnit, eventId, title, message)
        }
        NotificationReceiver.start(applicationContext, title, message, eventId)

        return Result.SUCCESS
    }
}