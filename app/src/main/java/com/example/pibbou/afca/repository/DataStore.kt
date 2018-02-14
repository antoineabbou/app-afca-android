package com.example.pibbou.afca.repository

import android.util.Log
import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter
import com.example.pibbou.afca.ui.list.FilterListAdapter

/**
 * Created by apinot on 12/02/2018.
 */

object DataStore {
    val repository = App.sInstance!!.getDataRepository()

    var events: ArrayList<Event>? = repository!!.getEvents()
    var currentEvents: ArrayList<Event>? = ArrayList()
    var currentFilters: MutableList<Int> = mutableListOf<Int>()

    var day:Int = 0

    var categoriesAdapter: CategoryListAdapter? = null
    var filterAdapter: FilterListAdapter? = null

    fun updateEventDatas(day: Int, public: Int) {

        // TODO: https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data - UPDATE ADAPTER

        currentEvents!!.clear()

        // Thanks to datarepo get all events
        val eventsByDay = repository!!.getEventsByDay(day)!!.toList()
        var test : List<Event>

        if (public != 0) {
            test = eventsByDay!!.filter { it.public == public }
        } else {
            test = eventsByDay
        }

        for(event in test!!){
            currentEvents!!.add(event)
            Log.i("JOSMAN", event.name)
        }

        categoriesAdapter!!.notifyDataSetChanged()
    }


   fun setFilter() {

       currentFilters!!.clear()

        var numbers: MutableList<Int> = mutableListOf<Int>()
        var numbersFiltered: List<Int>


        for(event in currentEvents!!){
            numbers!!.add(event.public!!)
        }

       numbers!!.add(0) // set All filter

       numbersFiltered = numbers!!.distinct()


       for(event in numbersFiltered){
           currentFilters.add(event)
       }

       filterAdapter!!.notifyDataSetChanged()
   }
}