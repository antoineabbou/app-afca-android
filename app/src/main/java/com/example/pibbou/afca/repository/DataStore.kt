package com.example.pibbou.afca.repository

import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter

/**
 * Created by apinot on 12/02/2018.
 */

object DataStore {
    val repository = App.sInstance!!.getDataRepository()

    var events: ArrayList<Event>? = repository!!.getEvents()
    var currentEvents: ArrayList<Event>? = ArrayList()

    var day:Int = 0

    var categoriesAdapter: CategoryListAdapter? = null


    fun updateEventDatas(day: Int) {

        // TODO: https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data - UPDATE ADAPTER

        currentEvents!!.clear()

        // Thanks to datarepo get all events
        val eventsByDay = repository!!.getEventsByDay(day)

        for(event in eventsByDay!!){
            currentEvents!!.add(event)
        }

        categoriesAdapter!!.notifyDataSetChanged()
    }
}