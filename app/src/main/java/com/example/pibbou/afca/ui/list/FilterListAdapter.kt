package com.example.pibbou.afca.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataStore

/**
 * Created by apinot on 12/02/2018.
 */
class FilterListAdapter(

        private val mContext: Context,
        private val filterList: List<Int?>?

) : RecyclerView.Adapter<FilterListAdapter.FilterItemRowHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag
            DataStore.updateEventDatas(DataStore.day, item.toString().toInt())
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FilterItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_filter, null)
        return FilterItemRowHolder(v)
    }

    override fun onBindViewHolder(holder: FilterItemRowHolder, i: Int) {
        // Get singleEvent
        var publicInt = filterList!!.get(i)
        var filterTitle = "TEST"

        when (filterList!!.get(i)) {
            0 -> filterTitle = "Tous"
            1 -> filterTitle = "Tout public"
            2 -> filterTitle = "Professionnel"
            3 -> filterTitle = "Enfants"
        }


        // Set text to card title
        holder.filterTitle.setText(filterTitle)

        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        with (holder.filterTitle) {
            tag = publicInt
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return filterList?.size ?: 0
    }

    inner class FilterItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        var filterTitle: Button

        init {
            // Get category title View
            this.filterTitle = view.findViewById(R.id.filterTitle)
        }
    }

}