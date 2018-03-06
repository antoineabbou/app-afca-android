package com.example.pibbou.afca.ui.main

import android.widget.TextView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.pibbou.afca.R


/**
 * Created by arnaudpinot on 06/03/2018.
 */
class DayFragment : android.support.v4.app.Fragment() {
    // Store instance variables
    private var title: String? = null
    private var page: Int = 0

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = getArguments().getInt("someInt", 0)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.fragment_day, container, false)
        val fragDay = view?.findViewById<View>(R.id.fragDay) as TextView
        fragDay.text = page.toString()
        return view
    }

    companion object {

        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int): DayFragment? {
            val fragmentFirst = DayFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            fragmentFirst.setArguments(args)
            return fragmentFirst
        }
    }
}