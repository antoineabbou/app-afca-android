package com.example.pibbou.afca.ui.infos

import android.os.Bundle
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
import android.widget.Button


class InformationsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var recycler_view_price_list: RecyclerView
    private var priceAdapter: PriceListAdapter? = null
    private val repository = App.sInstance.getDataRepository()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_informations)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val button = findViewById<Button>(R.id.more)
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
        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(MarkerOptions().position(sydney)
                .title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
