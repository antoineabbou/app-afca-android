package com.example.pibbou.afca.ui.infos

import android.os.Bundle
import android.view.View
import com.example.pibbou.afca.R
import com.example.pibbou.afca.ui.base.BaseActivity

class InformationsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun provideParentLayoutId(): Int {
        return R.layout.activity_informations
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }
}
