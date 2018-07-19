package mobile.hospetall.ps.isel.hospetallmobile.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication.Companion.TAG
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.EventActivity
import mobile.hospetall.ps.isel.hospetallmobile.models.Event


class NotificationReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "event_notification"
        const val CHANNEL_TITLE = "event_notification"
        const val EVENT_PARCEL = "event"

        fun start(context: Context, title: String, message: String?, id: Int){
            createNotification(context, title, message?:"", id)
        }

        fun start(context: Context, event: Event){
            Log.i(TAG, "Calling notification without receiver.")
            start(context, event.title, event.message, event.id)
        }

        private fun createNotification(context: Context, title: String, message: String, id: Int) {
            val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                createChannel(mNotifyMgr)

            val resultIntent = Intent(context, EventActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                    context,
                    id,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )

            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setContentTitle("Reminder: ${title}")
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .build()

            mNotifyMgr.notify(id, mBuilder)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun createChannel(mNotifyMgr: NotificationManager) {
            val id = CHANNEL_ID
            val name = CHANNEL_TITLE
            val description = ""
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(id, name, importance)

            // Configure the notification channel.
            mChannel.description = description
            mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotifyMgr.createNotificationChannel(mChannel)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val event = intent.getParcelableExtra<Event>(EVENT_PARCEL)
    }

}