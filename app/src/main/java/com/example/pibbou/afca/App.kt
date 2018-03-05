package com.example.pibbou.afca

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.pibbou.afca.repository.DataRepository
import com.example.pibbou.afca.repository.FavoriteManager
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonObject



/**
 * Created by arnaudpinot on 02/01/2018.
 */

class App : Application() {

    // Singleton instance
    var dataManager: DataRepository? = null
    var favoritesManager: FavoriteManager? = null

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


