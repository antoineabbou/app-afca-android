package com.example.pibbou.afca.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import java.util.ArrayList


/**
 * Created by arnaudpinot on 07/01/2018.
 */


class EventListAdapter(private val mContext: Context, private val eventList: ArrayList<Event>?) : RecyclerView.Adapter<EventListAdapter.SingleItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SingleItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_event_card, null)
        return SingleItemRowHolder(v)
    }


    override fun onBindViewHolder(holder: SingleItemRowHolder, i: Int) {
        // Get singleEvent
        val singleEvent = eventList!!.get(i)
        // Set text to card title
        holder.eventCardTitle.setText(singleEvent.name)
    }

    // Get Item Count - need because of Interface
    override fun getItemCount(): Int {
        return eventList?.size ?: 0
    }


    inner class SingleItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventCardTitle: TextView
        var eventCardImage: ImageView

        init {
            // Get card title view
            this.eventCardTitle = view.findViewById(R.id.eventCardTitle)
            // Get card Image view
            this.eventCardImage = view.findViewById(R.id.eventCardImage)
        }
    }
}
