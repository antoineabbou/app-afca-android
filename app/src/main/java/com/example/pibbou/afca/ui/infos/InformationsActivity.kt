package com.example.pibbou.afca.ui.infos

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionInflater
import android.view.View
import com.example.pibbou.afca.R
import com.example.pibbou.afca.ui.base.BaseActivity

class InformationsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setupWindowAnimations()
    }

    private fun setupWindowAnimations() {
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = TransitionInflater.from(this).inflateTransition(R.transition.fade)
            fade.duration = 500
            fade.excludeTarget(R.id.navBar, true)
            // set an exit transition
            window.enterTransition = fade
            window.exitTransition = fade
            window.returnTransition = fade
        } else {
            return
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun provideParentLayoutId(): Int {
        return R.layout.activity_informations
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }
}
