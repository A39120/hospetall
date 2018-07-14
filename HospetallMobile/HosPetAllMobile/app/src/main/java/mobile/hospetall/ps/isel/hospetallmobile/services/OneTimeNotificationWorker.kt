package mobile.hospetall.ps.isel.hospetallmobile.services

import android.util.Log
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.notifications.NotificationReceiver
import java.util.*
import java.util.concurrent.TimeUnit

class OneTimeNotificationWorker : Worker() {
    companion object {
        private const val TAG = "HPA/WORK/NOTIFICATION_1"
        private const val EVENT_ID = "EVENT_ID"
        private const val NAME = "one_time_event_notification"


        fun setUpWork(event: Event){
            val data = Data.Builder()
                    .putInt(EVENT_ID, event.id)
                    .build()

            val calendar = Calendar.getInstance()
            val delay = event.timedate - calendar.timeInMillis

            Log.i(TAG, "Setting up one time notification work: ${event.id} that will start in $delay milliseconds.")
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
        val eventId = inputData.getInt(EVENT_ID, -1)
        if(eventId == -1) {
            Log.e(TAG, "Event id could not be extracted from the Worker.")
            return Result.FAILURE
        }

        //Tries to get event from room database
        val live = ScheduleAccess().get(eventId).value
        if(live != null) {
            Log.i(TAG, "Got event with id ${live.id}")
            if(live.period > 0) {
                PeriodicNotificationWorker.scheduleNotification(live)
            } else {
                NotificationReceiver.start(applicationContext, live)
            }
        } else {
            Log.e(TAG, "Could not get event with $eventId")
            return Result.FAILURE
        }

        return Result.SUCCESS
    }
}