package com.example.pibbou.afca.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pibbou.afca.repository.entity.Event
import com.google.gson.Gson

/**
 * Created by aabbou on 14/02/2018.
 */

/**
 * Favorite Manager
 */
class FavoriteManager {

    val PREFS_NAME = "FNFA_AFCA"
    val FAVORITES = "Favorites"
    var currentFavorites = ArrayList<Event>()

    // keep favorites in SharedPreferences
    fun saveFavorites(context: Context, favorites: List<Event>) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE)
        editor = settings.edit()

        val gson = Gson()
        val jsonFavorites = gson.toJson(favorites)

        editor.putString(FAVORITES, jsonFavorites)

        editor.commit()

    }

    // Add one event to the list
    fun addFavorite(context: Context, event: Event) {

        var isInList = currentFavorites.filter {
            it.id === event.id
        }.count() > 0

        if(isInList == false) {
            currentFavorites.add(event)
            saveFavorites(context, currentFavorites)
        }

    }

    // Remove one event from the list
    fun removeFavorite(context: Context, event: Event) {

        var selectedFavorite = currentFavorites.filter {
            it.id === event.id
        }

        var isInList = selectedFavorite.count() > 0

        if(isInList == true) {
            currentFavorites.remove(selectedFavorite.first())
            saveFavorites(context, currentFavorites)
        }
    }

    // Write and save favorites
    fun setFavorites(context: Context): ArrayList<Event>? {
        val settings: SharedPreferences
        val favorites: List<Event>

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE)

        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson<Array<Event>>(jsonFavorites,
                    Array<Event>::class.java)

            favorites = favoriteItems.toList()

        } else {
            return null
        }

        for(favorite in favorites){
            currentFavorites.add(favorite)
        }

        return currentFavorites
    }


    // Get all favorites
    fun getFavorites(context: Context): List<Event>? {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favorites: List<Event>

        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson<Array<Event>>(jsonFavorites,
                    Array<Event>::class.java)

            favorites = favoriteItems.toList()
        }

        else {
            return null
        }

        return favorites
    }
}