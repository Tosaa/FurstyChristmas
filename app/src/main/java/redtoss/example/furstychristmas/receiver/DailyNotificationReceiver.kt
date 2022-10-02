package redtoss.example.furstychristmas.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import redtoss.example.furstychristmas.MainActivityCompose
import redtoss.example.furstychristmas.R

class DailyNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(
                NotificationChannel(CHANNEL, "Fursty Christmas Notification Channel", NotificationManager.IMPORTANCE_HIGH)
            )
            val notification = Notification.Builder(it, CHANNEL)
                .setSmallIcon(R.drawable.logo_razorbacks)
                .setContentTitle("Fursty Christmas")
                .setContentText("Workout heute schon gemacht?")
                .setAutoCancel(true)
                .setContentIntent(
                    PendingIntent.getActivities(
                        it,
                        2,
                        arrayOf(Intent(it, MainActivityCompose::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)),
                        0
                    )
                )
            notificationManager.notify(
                NOTIFICATION_ID,
                notification.build()
            )
        }
    }

    companion object {
        private val CHANNEL = "default"
        private val NOTIFICATION_ID = 123456
    }
}
