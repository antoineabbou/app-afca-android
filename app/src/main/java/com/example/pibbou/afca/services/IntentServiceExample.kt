package com.example.pibbou.afca.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.repository.entity.Event
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by antoine on 08/03/2018.
 */

class IntentServiceExample : Service() {
    // private var mServiceLooper: Looper? = null
    private var mServiceHandler: ServiceHandler? = null
    private var favoriteManager: FavoriteManager = FavoriteManager()
    private var notificationManager: com.example.pibbou.afca.repository.NotificationManager? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            try {
                notificationManager = com.example.pibbou.afca.repository.NotificationManager(applicationContext)
                compareToDeviceTime()
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        val thread = HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()

        // Get the HandlerThread's Looper and use it for our Handler
        val mServiceLooper = thread.looper
        mServiceHandler = ServiceHandler(mServiceLooper)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        val msg = mServiceHandler!!.obtainMessage()
        msg.arg1 = startId
        mServiceHandler!!.sendMessage(msg)

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }

    private fun compareToDeviceTime() {

        var currentTime: Date
        var datePlusFiveMinutes: Date

        val favorites: List<Event>? = favoriteManager.getFavorites(applicationContext)

        if (favorites!!.isNotEmpty()) {
            for (favorite in favorites!!) {
                var notified: Boolean? = null
                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        Log.i("test", "test")
                        currentTime = Calendar.getInstance().time

                        datePlusFiveMinutes = Date(System.currentTimeMillis() + 5 * 60 * 1000)

                        val duration = printDifference(currentTime, favorite.startingDate!!)

                        Log.i("favorite start date", favorite.startingDate.toString())
                        Log.i("currentTime", currentTime.toString())
                        Log.i("notified", notified.toString())

                        // If date is between current - 5min and current + 5 min, send notification
                        if (favorite.startingDate!!.before(datePlusFiveMinutes) && favorite.startingDate!!.after(currentTime) && notified === null) {
                            Log.i("Hello", "world")
                            notificationManager?.showNotification(applicationContext, "Un évenement près de chez vous dans $duration minutes :", favorite.name.toString(), favorite)
                            notified = true
                        }
                    }
                }, 0, 10000)
            }
            currentTime = Calendar.getInstance().getTime()
        }
    }


    private fun printDifference(startDate: Date, endDate: Date): Long {
        var different = endDate.time - startDate.time

        // Time constants
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60

        different %= hoursInMilli

        return (different / minutesInMilli) + 1
    }
}