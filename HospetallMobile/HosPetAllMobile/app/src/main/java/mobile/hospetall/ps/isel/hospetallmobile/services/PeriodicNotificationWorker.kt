package mobile.hospetall.ps.isel.hospetallmobile.services

import android.util.Log
import androidx.work.Data
import androidx.work.WorkManager
import androidx.work.Worker
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.notifications.NotificationReceiver
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

class PeriodicNotificationWorker : Worker() {
    companion object {
        private const val NAME = "PERIODIC_NOTIFICATION"
        private const val TAG = "HPA/WORK/NOTIF_PERIOD"

        fun scheduleNotification(period: Int, unitId: Int,  id: Int, title: String, message: String?){
            val number = DateUtils.getPeriod(unitId, period)
            val unit = DateUtils.getPeriodUnit(unitId)
            val data = packageData(title, message?:"", id)

            Log.i(TAG, "Setting up repetitive notification work: $number $unit")
            val periodWork =
                    androidx.work.PeriodicWorkRequest.Builder(PeriodicNotificationWorker::class.java, number.toLong(), unit)
                            .setInputData(data)
                            .addTag(NAME)
                            .build()
            WorkManager.getInstance()?.enqueue(periodWork)
        }

        fun scheduleNotification(event: Event) {
            event.run { scheduleNotification(period, periodUnit, id, title, message) }
        }

        private fun packageData(title: String, message: String, id: Int)=
            Data.Builder().apply {
                putInt(DatabaseColumns.ID, id)
                putString(Event.MESSAGE, message)
                putString(Event.TITLE, title)
            }.build()

        private fun getId(inputData: Data)= inputData.getInt(DatabaseColumns.ID, -1)
        private fun getTitle(inputData: Data) = inputData.getString(Event.TITLE, "")
        private fun getMessage(inputData: Data) = inputData.getString(Event.MESSAGE, "")
    }

    override fun doWork(): Result {
        Log.i(TAG, "Creating a periodic notification.")
        val id = getId(inputData)
        val title = getTitle(inputData)
        val message = getMessage(inputData)

        if(id < 0) {
            Log.e(TAG, "Failed to get event info.")
            return Result.FAILURE
        }

        Log.i(TAG, "Got event $id")
        NotificationReceiver.start(applicationContext, title?:"", message?:"", id)
        return Result.SUCCESS
    }

}
