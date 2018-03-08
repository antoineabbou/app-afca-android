package com.example.pibbou.afca.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.view.Window
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.ui.main.MainActivity
import android.app.ActivityOptions



/**
 * Created by antoine on 28/02/2018.
 */

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds
    private lateinit var mContext : Context

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            /*// Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this)
                startActivity(Intent(mContext, MainActivity::class.java), options.toBundle())
            } else {
                val intent = Intent(mContext, MainActivity::class.java)
                startActivity(intent)
            }*/

            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mContext = this

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}