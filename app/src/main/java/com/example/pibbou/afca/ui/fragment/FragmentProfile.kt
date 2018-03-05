package com.example.pibbou.afca.ui.fragment

/**
 * Created by antoineabbou on 11/01/2018.
 */
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.repository.NotificationManager
import com.example.pibbou.afca.ui.list.FavoriteListAdapter
import java.util.*



class FragmentProfile : Fragment() {

    private var view1: View? = null

    // Prepare eventsByDay array
    private var recycler_view_favorite_list: RecyclerView? = null
    private var favoriteAdapter: FavoriteListAdapter? = null
    private val favoriteManager = FavoriteManager()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1  = inflater!!.inflate(R.layout.fragment_profile, container, false)

        setupFavoriteList()

        var notificationManager = NotificationManager(context)


        doIt()

        return view1
    }

    private fun setupFavoriteList() {
        // Get recyclerview View
        recycler_view_favorite_list = view1!!.findViewById<View>(R.id.recycler_view_favorite_list) as RecyclerView

        // Set fixed size
        recycler_view_favorite_list!!.setHasFixedSize(true)

        //var test = favoriteManager

        // Prepare adapter
        favoriteAdapter = FavoriteListAdapter(context, favoriteManager?.getFavorites(context))

        recycler_view_favorite_list!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_favorite_list!!.adapter = favoriteAdapter
    }

    private fun doIt() {
        var currentTime: Date
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                currentTime = Calendar.getInstance().getTime();
                Log.i("tag", currentTime.toString())
            }
        }, 0, 2000)

        val yourDatabaseSavedDate = "2015-10-09 09:00:00.000000000"
        val date = Date()
        currentTime = Calendar.getInstance().getTime()


    }



}