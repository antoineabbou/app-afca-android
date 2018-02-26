package com.example.pibbou.afca.ui.infos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R

class InformationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informations)

        val intent = intent
        val id = intent.getIntExtra("id", 0)
    }
}
