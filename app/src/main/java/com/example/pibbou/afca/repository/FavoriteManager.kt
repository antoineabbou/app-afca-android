package com.example.pibbou.afca.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pibbou.afca.repository.entity.Event
import com.google.gson.Gson

/**
 * Created by aabbou on 14/02/2018.
 */
class FavoriteManager {

    val PREFS_NAME = "PRODUCT_APP"
    val FAVORITES = "Product_Favorite"
    var currentFavorites = ArrayList<Event>()

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


    fun addFavorite(context: Context, event: Event) {

        var isInList = currentFavorites.filter {
            it.id === event.id
        }.count() > 0

        if(isInList == false) {
            currentFavorites.add(event)
            saveFavorites(context, currentFavorites)
        }

    }


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

        /*settings.edit().clear().apply()
        settings.edit().commit()*/

        return currentFavorites
    }
}