package com.example.pibbou.afca.ui.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Category
import com.example.pibbou.afca.repository.entity.Event
import java.util.ArrayList

/**
 * Created by apinot on 12/02/2018.
 */
class FilterListAdapter(

        private val mContext: Context,
        private val filterList: ArrayList<Int?>?

) : RecyclerView.Adapter<FilterListAdapter.FilterItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FilterItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_filter, null)
        return FilterItemRowHolder(v)
    }

    override fun onBindViewHolder(holder: FilterItemRowHolder, i: Int) {
        // Get singleEvent
        var singleEvent = "TEST"

        when (filterList!!.get(i)) {
            0 -> singleEvent = "Tout public"
            1 -> singleEvent = "Professionnel"
            2 -> singleEvent = "Enfants"
        }


        // Set text to card title
        holder.filterTitle.setText(singleEvent)

        Log.i("YOUO", singleEvent)
    }

    override fun getItemCount(): Int {
        return filterList?.size ?: 0
    }

    inner class FilterItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        var filterTitle: TextView

        init {
            // Get category title View
            this.filterTitle = view.findViewById(R.id.filterTitle)
        }
    }

}