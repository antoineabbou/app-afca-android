package com.example.pibbou.afca.repository

import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Event

/**
 * Created by apinot on 12/02/2018.
 */

object DataStore {
    val repository = App.sInstance!!.getDataRepository()
    var events: ArrayList<Event>? = repository!!.getEvents()
    var currentEvents: ArrayList<Event>? = ArrayList()
}