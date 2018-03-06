package com.example.pibbou.afca.ui.list

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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

        holder?.favCardTitle?.setText(singleFav?.name)
        Picasso.with(mContext)
                .load("http://www.festival-film-animation.fr/images/photo1_La_Grande_histoire_dun_petit_trait.jpeg")
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

        init {
            // Get card title view
            this.favCardImage = view.findViewById(R.id.favCardImage)
            this.favCardTitle = view.findViewById(R.id.favCardTitle)
            this.favCardButton = view.findViewById(R.id.button_favorite)
        }
    }


}
