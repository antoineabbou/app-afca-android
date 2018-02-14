package com.example.pibbou.afca.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pibbou.afca.repository.entity.Event
import com.google.gson.Gson
import java.util.ArrayList

/**
 * Created by aabbou on 14/02/2018.
 */
class FavoriteManager {
    val PREFS_NAME = "PRODUCT_APP"
    val FAVORITES = "Product_Favorite"

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
        var favorites: MutableList<Event>? = getFavorites(context)

        if (favorites == null ) {
            favorites = ArrayList<Event>()
        }


        for (item in favorites) {
            if (item.id == event.id) {
                Log.i("INFO", item.id.toString())
                Log.i("INFO", event.id.toString())
            }
        }
        favorites!!.add(event)
        saveFavorites(context, favorites)

    }

    fun removeFavorite(context: Context, event: Event) {
        val favorites = getFavorites(context)
        if (favorites != null) {
            favorites.remove(event)
            saveFavorites(context, favorites)
        }
    }

    fun getFavorites(context: Context): ArrayList<Event>? {
        val settings: SharedPreferences
        var favorites: List<Event>

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE)

        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson<Array<Event>>(jsonFavorites,
                    Array<Event>::class.java)

            favorites = favoriteItems.toList()
            favorites = ArrayList<Event>(favorites)

        } else {
            return null
        }


        Log.i("INFO", favorites.toString())
        return favorites
    }
}