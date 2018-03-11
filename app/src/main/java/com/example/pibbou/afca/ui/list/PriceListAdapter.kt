package com.example.pibbou.afca.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Price
import java.util.ArrayList

/**
 * Created by antoine on 09/03/2018.
 */
class PriceListAdapter (private val mContext: Context, private val priceList: ArrayList<Price>?) : RecyclerView.Adapter<PriceListAdapter.SingleItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PriceListAdapter.SingleItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_price, null)
        return SingleItemRowHolder(v)
    }


    override fun onBindViewHolder(holder: SingleItemRowHolder, i: Int) {
        val singlePrice = priceList?.get(i)
        // Set text to card title
        holder.priceTitle.text = singlePrice?.name
        holder.priceValue.text = singlePrice?.price
        holder.priceCondition.text = singlePrice?.condition

        with (holder.itemView) {
            tag = singlePrice
        }
    }

    override fun getItemCount(): Int {
        return priceList?.size ?: 0
    }

    inner class SingleItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        var priceTitle: TextView
        var priceValue: TextView
        var priceCondition: TextView

        init {
            // Get card title view
            this.priceTitle = view.findViewById(R.id.priceTitle)
            this.priceValue = view.findViewById(R.id.priceValue)
            this.priceCondition = view.findViewById(R.id.priceCondition)
        }
    }
}