package com.example.pibbou.afca.repository

import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter
import com.example.pibbou.afca.ui.list.FilterListAdapter

/**
 * Created by apinot on 12/02/2018.
 */

object DataStore {
    val repository = App.sInstance.getDataRepository()

    var currentEvents: ArrayList<Event>? = ArrayList()
    var currentFilters: MutableList<Int> = mutableListOf<Int>()

    var day:Int = 0

    lateinit var categoriesAdapter: CategoryListAdapter
    lateinit var filterAdapter: FilterListAdapter


    fun updateEventDatas(day: Int, public: Int) {

        currentEvents?.clear()

        // Thanks to datarepo get all events
        val eventsByDay = repository!!.getEventsByDay(day)!!.toList()
        val test : List<Event>

        if (public != 0) {
            test = eventsByDay.filter { it.public == public }
        } else {
            test = eventsByDay
        }

        for(event in test){
            currentEvents?.add(event)
        }

        categoriesAdapter.notifyDataSetChanged()
    }


   fun setFilter() {

       currentFilters.clear()

        val numbers: MutableList<Int> = mutableListOf<Int>()
        val numbersFiltered: List<Int>


        for(event in currentEvents!!){
            numbers.add(event.public!!)
        }

       numbersFiltered = numbers.distinct()


       for(event in numbersFiltered){
           currentFilters.add(event)
       }

       filterAdapter.notifyDataSetChanged()
   }
}