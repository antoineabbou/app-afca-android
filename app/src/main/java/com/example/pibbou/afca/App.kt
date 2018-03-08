package com.example.pibbou.afca

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.pibbou.afca.repository.DataRepository
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.main.MainActivity
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by arnaudpinot on 02/01/2018.
 */

class App : Application() {

    // Singleton instance
    var dataManager: DataRepository? = null
    var favoritesManager: FavoriteManager? = null
    var notificationManager: com.example.pibbou.afca.repository.NotificationManager? = null


    private set // https://stackoverflow.com/questions/44755945/how-to-assign-new-value-if-you-setting-the-setter-private-in-kotlin

    // https://stackoverflow.com/questions/21818905/get-application-context-from-non-activity-singleton-class
    fun getContext(): Context {
        return this.getApplicationContext()
    }

    //////////////
    // On Create
    /////////////
    override fun onCreate() {
        super.onCreate()

        // Setup singleton instance
        sInstance = this
        // Load datas
        this.dataManager = DataRepository(this.getContext())
        this.dataManager?.deserializeDatas()

        // Set favorite
        this.favoritesManager = FavoriteManager()
        this.favoritesManager?.setFavorites(this.getContext())

        this.notificationManager = com.example.pibbou.afca.repository.NotificationManager(this.getContext())

        // favoritesList = this.favoritesManager!!.getFavorites(this.getContext())

        /*if (favoritesList?.size !== null) {
            for (favorite in favoritesList) {
                compareToDeviceTime(favorite)
            }
        }*/
        val favoritesList = this.favoritesManager?.getFavorites(this.getContext())
        Log.i("favoritesList", favoritesList.toString())
        compareToDeviceTime(favoritesList as ArrayList<Event>)

    }


    companion object {
        lateinit var sInstance: App
    }


    //////////////
    // Methods
    /////////////

    fun getDataRepository(): DataRepository? {
        return this.dataManager
    }

    fun getFavoriteManager(): FavoriteManager? {
        return this.favoritesManager
    }


    private fun compareToDeviceTime(favoritesList: ArrayList<Event>) {

        var currentTime: Date
        var datePlusFiveMinutes: Date
        var notified: Boolean? = null
        Log.i("Hello", "pre world")


        if (favoritesList.size > 0) {
            for (favorite in favoritesList) {
                Timer().scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        currentTime = Calendar.getInstance().time

                        datePlusFiveMinutes = Date(System.currentTimeMillis() + 5 * 60 * 1000)

                        val duration = printDifference(currentTime, favorite.startingDate!!)

                        Log.i("favorite start date", favorite.startingDate.toString())
                        Log.i("currentTime", currentTime.toString())

                        // If date is between current - 5min and current + 5 min, send notification
                        if (favorite.startingDate!!.before(datePlusFiveMinutes) && favorite.startingDate!!.after(currentTime) && notified === null) {
                            Log.i("Hello", "world")
                            notificationManager?.showNotification(getContext(), "Un évenement près de chez vous dans $duration minutes :", favorite.name.toString(), favorite)
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


