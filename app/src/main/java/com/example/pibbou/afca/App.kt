package com.example.pibbou.afca

import android.app.Application
import android.util.Log
import com.example.pibbou.afca.repository.DataRepository
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonObject







/**
 * Created by arnaudpinot on 02/01/2018.
 */
class App : Application() {

    private var dataManager: DataRepository? = null

    // Singleton instance
    private var sInstance: App? = null

    // Getter to access Singleton instance (https://stackoverflow.com/questions/29295364/android-static-application-getinstance)
    fun getInstance(): App? {
        return sInstance
    }




    //////////////
    // On Create
    /////////////
    override fun onCreate() {
        super.onCreate()

        // Setup singleton instance
        sInstance = this

        Log.d("Init", "Je load mon Data Repository dans mon App")

        // Load datas
        val dataManager = DataRepository(this.getApplicationContext())
        dataManager.loadDatas()
    }

}
