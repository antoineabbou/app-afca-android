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

    // https://stackoverflow.com/questions/21818905/get-application-context-from-non-activity-singleton-class
    fun getContext(): Context {
        return this.applicationContext
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
        // compareToDeviceTime(favoritesList as ArrayList<Event>)

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

}


