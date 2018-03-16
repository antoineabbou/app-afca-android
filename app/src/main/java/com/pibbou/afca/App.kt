package com.pibbou.afca

import android.app.Application
import android.content.Context
import com.pibbou.afca.repository.DataRepository
import com.pibbou.afca.repository.FavoriteManager


/**
 * Created by arnaudpinot on 02/01/2018.
 */

class App : Application() {

    // Singleton instance
    private var dataManager: DataRepository? = null
    private var favoritesManager: FavoriteManager? = null
    private var notificationManager: com.pibbou.afca.repository.NotificationManager? = null

    // https://stackoverflow.com/questions/21818905/get-application-context-from-non-activity-singleton-class
    private fun getContext(): Context {
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

        this.notificationManager = com.pibbou.afca.repository.NotificationManager(this.getContext())

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


