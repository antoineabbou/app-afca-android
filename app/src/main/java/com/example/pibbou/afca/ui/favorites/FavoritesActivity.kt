package com.example.pibbou.afca.ui.favorites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val intent = intent
        val id = intent.getIntExtra("id", 0)
    }
}
