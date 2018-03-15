package com.example.pibbou.afca.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.detail.DetailActivity

/**
 * Created by antoineabbou on 05/03/2018.
 */
 class NotificationManager (context: Context) {

    private val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val CHANNEL_ID = "com.example.pibbou.afca.ui"
    private val CHANNEL_NAME = "FNFA_AFCA"
    private val CHANNEL_DESCRIPTION = "Festival national du film d'animation AFCA"

    fun showNotification(context: Context, title: String, content: String, event: Event) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)

            channel.description = CHANNEL_DESCRIPTION
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID )
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setAutoCancel(true) // clear notification after click
        val intent = Intent(context, DetailActivity::class.java)
                .putExtra("id", event.id)
        val pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        mNotificationManager.notify(0, mBuilder.build())
    }


}