package com.pibbou.afca.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.pibbou.afca.App
import com.pibbou.afca.R
import com.pibbou.afca.repository.entity.Event
import com.pibbou.afca.ui.detail.DetailActivity
import com.squareup.picasso.Picasso
import java.util.ArrayList


/**
 * Created by arnaudpinot on 07/01/2018.
 */

class EventListAdapter(private val mContext: Context, private val eventList: ArrayList<Event>?) : RecyclerView.Adapter<EventListAdapter.SingleItemRowHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val onCheckedChanged: CompoundButton.OnCheckedChangeListener
    private var lastPosition = -1
    private val favoriteManager = App.sInstance.getFavoriteManager()
    var isInList: Boolean = false


    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Event

            val I = Intent(mContext, DetailActivity::class.java)
                    .putExtra("id", item.id)
            mContext.startActivity(I)
        }

        onCheckedChanged = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val event = buttonView.tag as Event
            showToast(event, isChecked)
        }
    }


    private fun showToast(event: Event, isChecked: Boolean) {
        isInList = if (!isChecked) {
            favoriteManager?.removeFavorite(mContext, event)
            false
        } else {
            favoriteManager?.addFavorite(mContext, event)
            true
        }
    }


    private fun setButton(toggle:ToggleButton, event: Event?) {
        toggle.isChecked = isInList != false

        with (toggle) {
            tag = event
            setOnCheckedChangeListener(onCheckedChanged)
        }

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SingleItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_event_card, null)
        return SingleItemRowHolder(v)
    }


    override fun onBindViewHolder(holder: SingleItemRowHolder, i: Int) {
        // Get singleEvent
        val singleEvent = eventList?.get(i)

        setAnimation(holder.itemView!!, i)

        // Set event title
        holder.eventCardTitle.text = singleEvent?.name?.toUpperCase()
        if(singleEvent?.author.isNullOrEmpty()) {
            holder.eventCardAuthor.text = "Auteur non communiqué"
        } else {
            holder.eventCardAuthor.text = singleEvent?.author
        }

        // Set event hour
        val hour = android.text.format.DateFormat.format("HH:mm", singleEvent?.startingDate) as String
        holder.eventCardHour.text = hour

        // Set event place
        holder.eventCardPlace.text = singleEvent?.place?.name

        // set event image
        Picasso.with(mContext)
            .load(singleEvent?.image)
            .into(holder.eventCardImage)

        // set button favorite
        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === singleEvent?.id
        }.count() > 0
        setButton(holder.eventCardFavorite, singleEvent)

        // set background color
        holder.cardViewBackground.setBackgroundColor(Color.parseColor(singleEvent?.category?.color))

        with (holder.itemView) {
            tag = singleEvent
            setOnClickListener(mOnClickListener)
        }

    }


    // Get Item Count - need because of Interface
    override fun getItemCount(): Int {
        return eventList?.size ?: 0
    }


    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


    inner class SingleItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventCardTitle: TextView = view.findViewById(R.id.eventCardTitle)
        var eventCardImage: ImageView = view.findViewById(R.id.eventCardImage)
        var eventCardAuthor: TextView = view.findViewById(R.id.eventCardAuthor)
        var eventCardHour: TextView = view.findViewById(R.id.eventCardHour)
        var eventCardPlace: TextView = view.findViewById(R.id.eventCardPlace)
        var eventCardFavorite: ToggleButton = view.findViewById(R.id.button_favorite)
        var cardViewBackground: LinearLayout = view.findViewById(R.id.cardViewBackground)
    }
}