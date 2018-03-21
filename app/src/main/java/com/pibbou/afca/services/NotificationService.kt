package com.pibbou.afca.services

import android.app.Service
import android.content.Intent
import android.os.*
import com.pibbou.afca.repository.FavoriteManager
import com.pibbou.afca.repository.entity.Event
import java.util.*


/**
 * Created by antoine on 08/03/2018.
 */

/**
 * External service to push notifications
 */
class NotificationService : Service() {
    private var mServiceHandler: ServiceHandler? = null
    private var favoriteManager: FavoriteManager = FavoriteManager()
    private var notificationManager: com.pibbou.afca.repository.NotificationManager? = null


    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            try {
                notificationManager = com.pibbou.afca.repository.NotificationManager(applicationContext)
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
        // Start up the thread running the service.
        // Set background priority so CPU-intensive work will not disrupt our UI.
        val thread = HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()

        // Get the HandlerThread's Looper and use it for our Handler
        val mServiceLooper = thread.looper
        mServiceHandler = ServiceHandler(mServiceLooper)
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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
    }


    // check every two minutes event time and device time
    private fun compareToDeviceTime() {

        var currentTime: Date
        var datePlusFifteenMinutes: Date

        val favorites: List<Event>? = favoriteManager.getFavorites(applicationContext)

        if (favorites!!.isNotEmpty()) {
            favorites.forEach { favorite ->
                var notified: Boolean? = null
                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        currentTime = Calendar.getInstance().time

                        datePlusFifteenMinutes = Date(System.currentTimeMillis() + 10 * 60 * 1000)

                        val duration = printDifference(currentTime, favorite.startingDate!!)

                        // If date is before + 5 min, send notification
                        if (favorite.startingDate!!.before(datePlusFifteenMinutes) && favorite.startingDate!!.after(currentTime) && notified === null) {
                            notificationManager?.showNotification(applicationContext, "Un évenement va commencer dans $duration minutes :", favorite.name.toString(), favorite)
                            // we set a notified variable to cancel sending of multiples notifications for same event
                            notified = true
                        }
                    }
                }, 0, 120000)
            }
            currentTime = Calendar.getInstance().getTime()
        }
    }


    // Return period between starting date and device date
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