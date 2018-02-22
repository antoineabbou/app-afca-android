package com.example.pibbou.afca.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import java.util.ArrayList

/**
 * Created by arnaudpinot on 07/01/2018.
 */

class FavoriteListAdapter(private val favoriteList: ArrayList<Event>?) : RecyclerView.Adapter<FavoriteListAdapter.FavItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_fav_card, null)
        return FavItemRowHolder(v)
    }

    override fun onBindViewHolder(holder: FavItemRowHolder, position: Int) {

        // Get singleEvent
        val singleFav = favoriteList?.get(position)
        // Set text to card title
        holder.favCardTitle.setText(singleFav?.name)

    }

    override fun getItemCount(): Int {
        return favoriteList?.size ?: 0
    }


    inner class FavItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        var favCardTitle: TextView

        init {
            // Get card title view
            this.favCardTitle = view.findViewById(R.id.favCardTitle)
        }
    }
}
