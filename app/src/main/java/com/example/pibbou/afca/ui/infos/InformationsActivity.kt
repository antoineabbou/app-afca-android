package com.example.pibbou.afca.ui.infos

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionInflater
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataRepository
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.ui.base.BaseActivity
import com.example.pibbou.afca.ui.list.FavoriteListAdapter
import com.example.pibbou.afca.ui.list.PriceListAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton


class InformationsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var recycler_view_price_list: RecyclerView
    private var priceAdapter: PriceListAdapter? = null
    private val repository = App.sInstance.getDataRepository()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }*/

        this.setupWindowAnimations()
        this.setNavBarActive()
    }

    private fun setNavBarActive() {
        val button = findViewById<View>(R.id.navBarInfos) as ImageButton
        button.isSelected = true
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

        // setContentView(R.layout.activity_informations)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val button = findViewById<Button>(R.id.more) as Button
        button.setOnClickListener(View.OnClickListener { v ->
            val uri = Uri.parse("http://www.festival-film-animation.fr/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        setupPriceList()
    }

    override fun provideParentLayoutId(): Int {
        return R.layout.activity_informations
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        val home = LatLng(48.115186, -1.682684)

        val places = repository?.getPlaces()

        for (place in places!!) {
            val lat = place.lat
            val long = place.long
            val latlng = LatLng(lat!!, long!!)
            googleMap.addMarker(MarkerOptions().position(latlng)
                    .title(place.name))
        }

        googleMap.addMarker(MarkerOptions().position(home)
                .title("Marker in home"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
    }

    private fun setupPriceList() {
        // Get recyclerview View
        recycler_view_price_list = findViewById<View>(R.id.recycler_view_price_list) as RecyclerView

        // Set fixed size
        recycler_view_price_list.setHasFixedSize(true)

        // Prepare adapter
        priceAdapter = PriceListAdapter(applicationContext, repository!!.getPrices())

        recycler_view_price_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_price_list.adapter = priceAdapter
    }
}
