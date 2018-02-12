package com.example.pibbou.afca.repository

import android.content.Context
import android.util.Log
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Category
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.repository.entity.Place
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import com.google.gson.GsonBuilder



/**
 * Created by arnaudpinot on 02/01/2018.
 * Class to manage app datas
 */
class DataRepository constructor(contextArg: Context) {

    private var context: Context
    private var places: ArrayList<Place> = ArrayList()
    private var categories: ArrayList<Category> = ArrayList()
    private var events: ArrayList<Event> = ArrayList()

    /**
     * Initialization
     */
    init {
        context = contextArg
    }


    /**
     *  Load all datas
     */
    fun deserializeDatas(){
        deserializePlaces()
        deserializeCategories()
        deserializeEvents()
        Log.d("GSON deserialize", "END")
    }



    /**
     *  GSON deserialize
     */

    // Places
    private fun deserializePlaces() {
        val jsonDatas = jsonToString(R.raw.places, context)

        val gson = Gson()
        val jsonPlaces = gson.fromJson(jsonDatas, Array<Place>::class.java)

        for(place in jsonPlaces){
            places.add(place)
        }

        Log.d("GSON deserialize", jsonDatas)
    }

    // Categories
    private fun deserializeCategories() {
        val jsonDatas = jsonToString(R.raw.categories, context)

        val gson = Gson()
        val jsonCategories = gson.fromJson(jsonDatas, Array<Category>::class.java)

        for(category in jsonCategories){
            categories.add(category)
        }

        Log.d("GSON deserialize", jsonDatas)
    }


    // Events
    private fun deserializeEvents() {
        val jsonDatas = jsonToString(R.raw.events, context)

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:SS").create()
        val jsonEvents = gson.fromJson(jsonDatas, Array<Event>::class.java)

        for(event in jsonEvents){
            val placeId = event.placeId!!
            val categoryId = event.categoryId!!
            event.place = this@DataRepository.findPlaceById(placeId)
            event.category = this@DataRepository.findCategoryById(categoryId)
            events.add(event)
        }
        
        Log.d("GSON deserialize", jsonDatas)
    }



    /**
     * Json to string
     */
    // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android
    private fun jsonToString(path: Int?, context: Context): String? {
        // Var to return
        var json: String? = null

        try {
            // Get file from ressources
            val jsonFile = context.resources.openRawResource(path!!)

            // Returns an estimate of the number of bytes that can be read from this input stream
            val size = jsonFile.available()

            // Creates a new array of the specified size, with all elements initialized to zero.
            val buffer = ByteArray(size)

            // Read
            jsonFile.read(buffer)

            // Then close
            jsonFile.close()

            // Finally String the buffer
            json = String(buffer)

            // Catch error
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        // Return json in String type
        return json
    }



    /**
     * Methods
     */

    // Function to save places into each events
    fun findPlaceById(id:Int): Place? {

        val result = places.filter { it.id == id + 1 }


        if (result.isNotEmpty()) {
            return result.first()
        }

        return null
    }

    // Function to save category into each events
    fun findCategoryById(id:Int): Category? {
        val result = categories.filter { it.id == id }

        if (result.isNotEmpty()) {
            return result.first()
        }

        return null
    }

    // Return all events
    fun getEvents(): ArrayList<Event>? {

        if (events.isNotEmpty()) {
            return events
        }

        return null
    }

    // Return events by day
    fun getEventsByDay(day:Int): ArrayList<Event>? {

        val result = events.filter { it.day == day + 1 }

        if (result.isNotEmpty()) {
            return ArrayList(result)
        }

        return null
    }

    // Find event by ID
    fun findEventById(id:Int): Event? {
        val result = events.filter { it.id == id }

        if (result.isNotEmpty()) {
            return result.first()
        }

        return null
    }
}