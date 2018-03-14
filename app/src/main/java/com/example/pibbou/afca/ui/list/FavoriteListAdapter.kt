package com.example.pibbou.afca.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.NotificationManager
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.detail.DetailActivity
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by arnaudpinot on 07/01/2018.
 */

class FavoriteListAdapter(private val mContext: Context, private val favoriteList: ArrayList<Event>?) : RecyclerView.Adapter<FavoriteListAdapter.FavItemRowHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val bOnClickListener: View.OnClickListener


    // Favorite manager call
    val favoriteManager = App.sInstance!!.getFavoriteManager()
    var isInList: Boolean = false
    val notificationManager = NotificationManager(mContext)
    val dataRepository = App.sInstance!!.getDataRepository()



    init {
        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Event
            // Toast.makeText(mContext, item.name, Toast.LENGTH_SHORT).show()

            val I = Intent(mContext, DetailActivity::class.java)
                    .putExtra("id", item.id)
            mContext.startActivity(I)
        }

        bOnClickListener = View.OnClickListener { b ->
            Log.i("info", "i am clicking")
            val event = b.tag as Event
            favoriteManager?.removeFavorite(mContext, event)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_fav_card, null)
        return FavItemRowHolder(v)
    }


    fun setButton(button: Button, event: Event) {

        with (button) {
            tag = event
            setOnClickListener(bOnClickListener)
        }

    }

    override fun onBindViewHolder(holder: FavItemRowHolder?, position: Int) {

        // Get singleEvent
        val singleFav = favoriteList?.get(position)
        // Set text to card title

        holder?.favCardContainer?.setBackgroundColor(Color.parseColor(singleFav?.category?.color))
        holder?.favCardBackground?.setBackgroundColor(Color.parseColor(singleFav?.category?.color))


        holder?.favCardTitle?.setText(singleFav?.name)

        if(singleFav?.author.isNullOrEmpty()) {
            holder?.favCardAuthor?.setText("Auteur non communiqué")
        } else {
            holder?.favCardAuthor?.setText(singleFav?.author)
        }

        val hour = android.text.format.DateFormat.format("HH:mm", singleFav?.startingDate) as String
        holder?.favCardHour?.setText(hour)

        val place = dataRepository!!.findPlaceById(singleFav?.id!!)
        holder?.favCardPlace?.setText(place?.name)

        Picasso.with(mContext)
                .load(singleFav.image)
                .into(holder?.favCardImage)


        // Set text to card title
        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === singleFav?.id
        }.count() > 0

        // if (singleFav != null) compareToDeviceTime(singleFav)

        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        with (holder?.itemView) {
            this!!.tag = singleFav
            setOnClickListener(mOnClickListener)
        }

        if (holder != null) {
            if (singleFav != null) {
                setButton(holder.favCardButton, singleFav)
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteList?.size ?: 0
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
            // Get card title view
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
