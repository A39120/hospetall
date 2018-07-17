package mobile.hospetall.ps.isel.hospetallmobile.services

import android.util.Log
import androidx.work.WorkManager
import androidx.work.Worker
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.notifications.NotificationReceiver
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

            Log.i(TAG, "Setting up one time notification work: $id that will start in $delay milliseconds.")
            val oneTimeWork =
                    androidx.work.OneTimeWorkRequest.Builder(PeriodicNotificationWorker::class.java)
                            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                            .setInputData(data)
                            .addTag(NAME)
                            .build()

            WorkManager.getInstance()?.enqueue(oneTimeWork)
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

        val period = inputData.getLong(Event.PERIOD, -1)
        val periodUnit = inputData.getInt(Event.PERIOD_UNIT, 0)
        val title = inputData.getString(Event.TITLE, "")?:""
        val message = inputData.getString(Event.MESSAGE, "")

        Log.i(TAG, "Got event with id $id")
        if(period > 0)
            PeriodicNotificationWorker.scheduleNotification(period, periodUnit, eventId, title, message)
        else
            NotificationReceiver.start(applicationContext, title, message, eventId)

        return Result.SUCCESS
    }
}