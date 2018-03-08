package com.example.pibbou.afca.ui.base

import android.app.ActivityOptions
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.os.Build
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

        val programButton = findViewById<View>(R.id.navBarProg) as Button
        val programIntent = Intent(this@BaseActivity, MainActivity::class.java)
        setButtonProgram(programIntent, 0, programButton)

        val infoButton = findViewById<View>(R.id.navBarInfos) as Button
        val infoIntent = Intent(this@BaseActivity, InformationsActivity::class.java)
        setButtonProgram(infoIntent, 1, infoButton)

        val favoriteButton = findViewById<View>(R.id.navBarFavs) as Button
        val favoriteIntent = Intent(this@BaseActivity, FavoritesActivity::class.java)
        setButtonProgram(favoriteIntent, 2, favoriteButton)
    }

    fun setButtonProgram(i: Intent, position: Int, button: Button) {

        if (DataStore.intentPosition != position) {
            button.setOnClickListener(View.OnClickListener {
                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(this)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(i, options.toBundle())
                } else {
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(i)
                }

                DataStore.intentPosition = position
            })
        }
    }

    abstract fun provideParentLayoutId(): Int

    abstract fun setParentLayout(): View
}