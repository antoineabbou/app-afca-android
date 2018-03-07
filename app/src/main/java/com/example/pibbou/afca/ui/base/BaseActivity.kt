package com.example.pibbou.afca.ui.base

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Button
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.ui.main.MainActivity
import com.example.pibbou.afca.ui.favorites.FavoritesActivity
import com.example.pibbou.afca.ui.infos.InformationsActivity


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideParentLayoutId())

        this.setProgramButton()
        this.setInformationsButton()
        this.setFavoritesButton()
    }

    fun setProgramButton() {
        val position = 0

        if (DataStore.intentPosition != position) {
            val button = findViewById<View>(R.id.navBarProg) as Button
            button.setOnClickListener(View.OnClickListener {
                val i = Intent(this@BaseActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)
                DataStore.intentPosition = 0
            })
        }
    }

    fun setInformationsButton() {
        val position = 1

        if (DataStore.intentPosition != position) {
            val button = findViewById<View>(R.id.navBarInfos) as Button
            button.setOnClickListener(View.OnClickListener {
                val i = Intent(this@BaseActivity, InformationsActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)
                DataStore.intentPosition = 1
            })
        }
    }

    fun setFavoritesButton() {
        val position = 2

        if (DataStore.intentPosition != position) {
            val button = findViewById<View>(R.id.navBarFavs) as Button
            button.setOnClickListener(View.OnClickListener {
                val i = Intent(this@BaseActivity, FavoritesActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(i)
                DataStore.intentPosition = 2
            })
        }
    }

    abstract fun provideParentLayoutId(): Int

    abstract fun setParentLayout(): View
}