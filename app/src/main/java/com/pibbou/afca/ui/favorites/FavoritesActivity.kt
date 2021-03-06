package com.pibbou.afca.ui.favorites

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pibbou.afca.App
import com.pibbou.afca.R
import com.pibbou.afca.ui.base.BaseActivity
import com.pibbou.afca.ui.list.FavoriteListAdapter
import android.widget.ImageButton
import android.widget.ToggleButton
import com.pibbou.afca.services.NotificationService


class FavoritesActivity : BaseActivity() {

    // Prepare eventsByDay array
    private lateinit var recycler_view_favorite_list: RecyclerView
    private var favoriteAdapter: FavoriteListAdapter? = null

    // Favorite manager call
    val favoriteManager = App.sInstance.getFavoriteManager()

    // Notification autorisation
    var notificationAutho: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup List
        this.setupFavoriteList()

        val toggle = findViewById<ToggleButton>(R.id.notification_button) as ToggleButton
        val intent = Intent(this, NotificationService::class.java)

        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            notificationAutho = isChecked

            if (isChecked) {

                buttonView.setTextColor(Color.parseColor("#68DF9D"))
                buttonView.setTypeface(null, Typeface.BOLD)
                startService(intent)

            } else if (!isChecked) {

                buttonView.setTextColor(Color.parseColor("#D8D8D8"))
                buttonView.setTypeface(null, Typeface.NORMAL)
                stopService(intent)
                
            }
        }

        this.setNavBarActive()
    }


    private fun setNavBarActive() {
        val button = findViewById<View>(R.id.navBarFavs) as ImageButton
        button.isSelected = true
    }


    override fun onResume() {
        super.onResume()
        favoriteAdapter?.notifyDataSetChanged()
    }


    override fun provideParentLayoutId(): Int {
        return R.layout.activity_favorites
    }


    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }


    private fun setupFavoriteList() {
        // Get recyclerview View
        recycler_view_favorite_list = findViewById<View>(R.id.recycler_view_favorite_list) as RecyclerView

        // Set fixed size
        recycler_view_favorite_list.setHasFixedSize(true)

        val favoriteDatas = favoriteManager?.currentFavorites
        favoriteDatas?.sortBy { it.startingDate }

        // Prepare adapter
        favoriteAdapter = FavoriteListAdapter(this, favoriteDatas)

        recycler_view_favorite_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_favorite_list.adapter = favoriteAdapter
    }


    fun toggleEmptyMessage(show: Boolean) {
        val emptyMessage = findViewById<View>(R.id.noFavorite) as View

        if (show) {
            emptyMessage.visibility = View.VISIBLE
        } else {
            emptyMessage.visibility = View.GONE
        }
    }
}
