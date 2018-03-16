package com.example.pibbou.afca.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.detail.DetailActivity
import com.example.pibbou.afca.ui.favorites.FavoritesActivity
import com.squareup.picasso.Picasso
import java.util.*
import android.view.animation.AnimationUtils


/**
 * Created by arnaudpinot on 07/01/2018.
 */

class FavoriteListAdapter(private val mContext: Context, private val favoriteList: ArrayList<Event>?) : RecyclerView.Adapter<FavoriteListAdapter.FavItemRowHolder>() {

    private val mOnClickListener: View.OnClickListener
    private lateinit var bOnClickListener: View.OnClickListener

    private var context: Context = mContext
    var isInList: Boolean = false
    private var lastPosition = -1

    // Favorite manager call
    private val favoriteManager = App.sInstance!!.getFavoriteManager()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Event
            val I = Intent(mContext, DetailActivity::class.java)
                    .putExtra("id", item.id)
            mContext.startActivity(I)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_fav_card, null)
        checkLengthFavorites()
        return FavItemRowHolder(v)
    }


    private fun setButton(button: Button, event: Event) {
        with (button) {
            tag = event
            setOnClickListener(bOnClickListener)
        }
    }

    private fun checkLengthFavorites() {
        if (favoriteManager?.getFavorites(mContext)!!.isEmpty()) {
            if (context is FavoritesActivity) {
                (context as FavoritesActivity).toggleEmptyMessage(true)
            }
        } else {
            if (context is FavoritesActivity) {
                (context as FavoritesActivity).toggleEmptyMessage(false)
            }
        }
    }

    override fun onBindViewHolder(holder: FavItemRowHolder?, position: Int) {
        setAnimation(holder?.itemView!!, position)

        // Get singleEvent
        val singleFav = favoriteList?.get(position)

        // background color
        holder.favCardContainer.setBackgroundColor(Color.parseColor(singleFav?.category?.color))
        holder.favCardBackground.setBackgroundColor(Color.parseColor(singleFav?.category?.color))

        // fav title
        holder.favCardTitle.text = singleFav?.name?.toUpperCase()

        // fav author
        if(singleFav?.author.isNullOrEmpty()) {
            holder.favCardAuthor.text = "Auteur non communiqué"
        } else {
            holder.favCardAuthor.text = singleFav?.author
        }

        // fav hour
        val hour = android.text.format.DateFormat.format("HH:mm", singleFav?.startingDate) as String
        holder.favCardHour.text = hour

        // fav place
        holder.favCardPlace.text = singleFav?.place?.name

        // fav image
        Picasso.with(mContext)
                .load(singleFav?.image)
                .into(holder.favCardImage)

        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === singleFav?.id
        }.count() > 0

        with (holder.itemView) {
            this!!.tag = singleFav
            setOnClickListener(mOnClickListener)
        }

        bOnClickListener = View.OnClickListener { b ->
            val event = b.tag as Event
            favoriteManager.removeFavorite(mContext, event)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount);
            holder.itemView.animate().alpha(0.toFloat()).setDuration(400)
            checkLengthFavorites()

        }

        if (singleFav != null) {
            setButton(holder.favCardButton, singleFav)
        }


    }

    override fun getItemCount(): Int {
        return favoriteList?.size ?: 0
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


    inner class FavItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        var favCardTitle: TextView
        var favCardImage: ImageView
        var favCardButton: Button
        var favCardAuthor: TextView
        var favCardHour: TextView
        var favCardPlace: TextView
        var favCardContainer: ConstraintLayout
        var favCardBackground: LinearLayout

        init {
            this.favCardImage = view.findViewById(R.id.favCardImage)
            this.favCardTitle = view.findViewById(R.id.favCardTitle)
            this.favCardButton = view.findViewById(R.id.button_favorite)
            this.favCardAuthor = view.findViewById(R.id.favCardAuthor)
            this.favCardHour = view.findViewById(R.id.favCardHour)
            this.favCardPlace = view.findViewById(R.id.favCardPlace)
            this.favCardContainer = view.findViewById(R.id.favCardContainer)
            this.favCardBackground = view.findViewById(R.id.cardViewBackground)
        }
    }
}
