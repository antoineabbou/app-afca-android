package com.example.pibbou.afca.ui.list

/**
 * Created by antoineabbou on 11/01/2018.
 */

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.pibbou.afca.R



class FragmentOne : Fragment() {

    var tv_name: TextView? = null
    var rel_main: RelativeLayout? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater!!.inflate(R.layout.fragment_layout, container, false)

        rel_main=view?.findViewById<RelativeLayout>(R.id.rel_main) as RelativeLayout
        rel_main?.setBackgroundColor(Color.CYAN)
        tv_name = view?.findViewById<TextView>(R.id.tv_name) as TextView
        tv_name?.text = "W3Adda First Tab"


        return view
    }
}