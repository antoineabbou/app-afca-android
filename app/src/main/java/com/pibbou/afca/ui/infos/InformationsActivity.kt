package com.pibbou.afca.ui.infos

import android.os.Build
import android.os.Bundle
import android.view.View
import com.pibbou.afca.App
import com.pibbou.afca.R
import com.pibbou.afca.ui.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.res.Resources
import android.widget.ImageButton
import android.support.annotation.RequiresApi
import android.widget.TextView
import com.github.aakira.expandablelayout.ExpandableRelativeLayout
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions



class InformationsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var expandableLayoutPrice: ExpandableRelativeLayout
    private lateinit var expandableLayoutMove: ExpandableRelativeLayout
    private lateinit var expandableLayoutEat: ExpandableRelativeLayout
    private lateinit var expandableLayoutPartners: ExpandableRelativeLayout
    private val repository = App.sInstance.getDataRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setNavBarActive()
        this.setExpandable()

        this.setPrices()
        this.setMove()
        this.setEat()
        this.setOrganizer()

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun setExpandable() {
        expandableLayoutPrice = findViewById<View>(R.id.expandableLayoutPrice) as ExpandableRelativeLayout
        expandableLayoutMove = findViewById<View>(R.id.expandableLayoutMove) as ExpandableRelativeLayout
        expandableLayoutEat = findViewById<View>(R.id.expandableLayoutEat) as ExpandableRelativeLayout
        expandableLayoutPartners = findViewById<View>(R.id.expandableLayoutPartners) as ExpandableRelativeLayout

        expandableLayoutPrice.collapse()
        expandableLayoutMove.collapse()
        expandableLayoutEat.collapse()
        expandableLayoutPartners.collapse()
    }


    fun expandablePrice(view: View) {
        expandableLayoutPrice.toggle() // toggle expand and collapse
    }


    fun expandableMove(view: View) {
        expandableLayoutMove.toggle()
    }


    fun expandableEat(view: View) {
        expandableLayoutEat.toggle() // toggle expand and collapse
    }


    fun expandablePartners(view: View) {
        expandableLayoutPartners.toggle() // toggle expand and collapse
    }


    private fun setNavBarActive() {
        val button = findViewById<View>(R.id.navBarInfos) as ImageButton
        button.isSelected = true
    }


    override fun provideParentLayoutId(): Int {
        return R.layout.activity_informations
    }


    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMapReady(googleMap: GoogleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json))

            if (!success) {
            }
        } catch (e: Resources.NotFoundException) {
        }

        // Rennes center
        val center = LatLng(48.115186, -1.682684)

        val places = repository?.getPlaces()

        for ((i, place) in places!!.withIndex()) {
            var marker = i
            val lat = place.lat
            val long = place.long
            val latlng = LatLng(lat!!, long!!)

            // set markers for each place
            when(i) {
                0 -> marker = R.drawable.markerblue;
                1 -> marker = R.drawable.markergreen
                2 -> marker = R.drawable.markeryellow
                3 -> marker = R.drawable.markerpurple
                4 -> marker = R.drawable.markerpink
                5 -> marker = R.drawable.markerred
                6 -> marker = R.drawable.markerviolet
            }

            googleMap.addMarker(MarkerOptions().position(latlng)
                    .title(place.name)
                    .icon(BitmapDescriptorFactory.fromResource(marker)))

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(center))
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

        }
    }


    private fun setPrices() {

        val list_price_item_1: TextView = findViewById(R.id.list_price_item_1)
        val content_list_price_item_1 = "Ouverture de la billetterie 30 minutes avant le début de chaque séance pour toutes les salles."
        list_price_item_1.setText(content_list_price_item_1)

        val list_price_item_2: TextView = findViewById(R.id.list_price_item_2)
        val content_list_price_item_2 = "Pour les séances du TNB uniquement, pré-vente à partir du 4 avril sur toute la durée du Festival."
        list_price_item_2.setText(content_list_price_item_2)

        val list_price_item_3: TextView = findViewById(R.id.list_price_item_3)
        val content_list_price_item_3 = "Carte abonnée des salles partenaires (TNB, Arvor et Le Grand Logis) acceptée hors ciné concert. "
        list_price_item_3.setText(content_list_price_item_3)

        //////////////
        //////////////

        val price_title: TextView = findViewById(R.id.price_title)
        val content_price_title = "Tarifs tout public"
        price_title.setText(content_price_title)

        val price_title_2: TextView = findViewById(R.id.price_title_2)
        val content_price_title_2 = "Tarifs scolaires et structures"
        price_title_2.setText(content_price_title_2)

        //////////////
        //////////////

        val price_type_1: TextView = findViewById(R.id.price_type_1)
        val content_price_type_1 = "Tarif unique"
        price_type_1.setText(content_price_type_1)

        val price_type_2: TextView = findViewById(R.id.price_type_2)
        val content_price_type_2 = "Ciné concert*"
        price_type_2.setText(content_price_type_2)

        val price_type_3: TextView = findViewById(R.id.price_type_3)
        val content_price_type_3 = "Carte sortir et groupe**"
        price_type_3.setText(content_price_type_3)

        val price_type_4: TextView = findViewById(R.id.price_type_4)
        val content_price_type_4 = "Carnet de fidélité***"
        price_type_4.setText(content_price_type_4)

        val price_type_5: TextView = findViewById(R.id.price_type_5)
        val content_price_type_5 = "Projection"
        price_type_5.setText(content_price_type_5)

        val price_type_6: TextView = findViewById(R.id.price_type_6)
        val content_price_type_6 = "Atelier"
        price_type_6.setText(content_price_type_6)


        //////////////
        //////////////

        val price_number_1: TextView = findViewById(R.id.price_number_1)
        val content_price_number_1 = "6€"
        price_number_1.setText(content_price_number_1)

        val price_number_2: TextView = findViewById(R.id.price_number_2)
        val content_price_number_2 = "8€"
        price_number_2.setText(content_price_number_2)

        val price_number_3: TextView = findViewById(R.id.price_number_3)
        val content_price_number_3 = "3€"
        price_number_3.setText(content_price_number_3)

        val price_number_4: TextView = findViewById(R.id.price_number_4)
        val content_price_number_4 = "25€"
        price_number_4.setText(content_price_number_4)

        val price_number_5: TextView = findViewById(R.id.price_number_5)
        val content_price_number_5 = "3€"
        price_number_5.setText(content_price_number_5)

        val price_number_6: TextView = findViewById(R.id.price_number_6)
        val content_price_number_6 = "4€"
        price_number_6.setText(content_price_number_6)

        //////////////
        //////////////

        val remark_price: TextView = findViewById(R.id.remark_price)
        val content_remark_price = "* Carnet de fidélité, tarif de groupe et carte abonné non acceptés"
        remark_price.setText(content_remark_price)

        val remark_price_2: TextView = findViewById(R.id.remark_price_2)
        val content_remark_price_2 = "** À partir de 10 personnes"
        remark_price_2.setText(content_remark_price_2)

        val remark_price_3: TextView = findViewById(R.id.remark_price_3)
        val content_remark_price_3 = "*** 5 places"
        remark_price_3.setText(content_remark_price_3)
    }


    private fun setMove() {
        val city_1: TextView = findViewById(R.id.city_1)
        val content_city_1 = "Rennes"
        city_1.setText(content_city_1)

        val city_2: TextView = findViewById(R.id.city_2)
        val content_city_2 = "Bruz"
        city_2.setText(content_city_2)

        //////////////
        /////////////

        val place_1: TextView = findViewById(R.id.place_1)
        val content_place_1 = "TNB"
        place_1.setText(content_place_1)

        //////////////
        /////////////

        val address_1: TextView = findViewById(R.id.address_1)
        val content_address_1 = "1 Rue Saint-Hélier, 35040 Rennes"
        address_1.setText(content_address_1)

        //////////////
        /////////////

        val phone_1: TextView = findViewById(R.id.phone_1)
        val content_phone_1 = "02 99 31 55 33"
        phone_1.setText(content_phone_1)
        //////
        val bus_1: TextView = findViewById(R.id.bus_1)
        val content_bus_1 = "Arrêt : Liberté TNB / Métro Charles de Gaulle"
        bus_1.setText(content_bus_1)

        //////////////
        /////////////

        val place_2: TextView = findViewById(R.id.place_2)
        val content_place_2 = "Arvor"
        place_2.setText(content_place_2)

        //////////////
        /////////////

        val address_2: TextView = findViewById(R.id.address_2)
        val content_address_2 = "29 Rue d'Antrain, 35700 Rennes"
        address_2.setText(content_address_2)

        //////////////
        /////////////

        val phone_2: TextView = findViewById(R.id.phone_2)
        val content_phone_2 = "02 99 38 78 04"
        phone_2.setText(content_phone_2)

        //////////////
        /////////////

        val bus_2: TextView = findViewById(R.id.bus_2)
        val content_bus_2 = "Arrêt : Sainte-Anne ou Hôtel Dieu / Métro"
        bus_2.setText(content_bus_2)

        //////////////
        /////////////

        val place_3: TextView = findViewById(R.id.place_3)
        val content_place_3 = "ESRA"
        place_3.setText(content_place_3)

        //////////////
        /////////////

        val address_3: TextView = findViewById(R.id.address_3)
        val content_address_3 = "1 Rue Xavier Grall, 35700 Rennes"
        address_3.setText(content_address_3)

        //////////////
        /////////////

        val phone_3: TextView = findViewById(R.id.phone_3)
        val content_phone_3 = "02 99 31 55 33"
        phone_3.setText(content_phone_3)

        //////////////
        /////////////

        val bus_3: TextView = findViewById(R.id.bus_3)
        val content_bus_3 = "Arrêt : Liberté TNB / Métro Charles de Gaulle"
        bus_3.setText(content_bus_3)

        //////////////
        /////////////

        val place_4: TextView = findViewById(R.id.place_4)
        val content_place_4 = "Esplanade Charles de Gaulle"
        place_4.setText(content_place_4)

        //////////////
        /////////////

        val address_4: TextView = findViewById(R.id.address_4)
        val content_address_4 = "Esplanade Charles de Gaulle, 35000 Rennes"
        address_4.setText(content_address_4)

        //////////////
        /////////////

        val phone_4: TextView = findViewById(R.id.phone_4)
        val content_phone_4 = "02 99 31 55 33"
        phone_4.setText(content_phone_4)

        //////////////
        /////////////

        val bus_4: TextView = findViewById(R.id.bus_4)
        val content_bus_4 = "Arrêt : Liberté TNB / Métro Charles de Gaulle"
        bus_4.setText(content_bus_4)


        //////////////
        /////////////

        val place_5: TextView = findViewById(R.id.place_5)
        val content_place_5 = "France 3 Bretagne"
        place_5.setText(content_place_5)

        //////////////
        /////////////

        val address_5: TextView = findViewById(R.id.address_5)
        val content_address_5 = "9 Avenue Jean Janvier, 35000 Rennes"
        address_5.setText(content_address_5)

        //////////////
        /////////////

        val phone_5: TextView = findViewById(R.id.phone_5)
        val content_phone_5 = "02 99 31 55 33"
        phone_5.setText(content_phone_5)

        //////////////
        /////////////

        val bus_5: TextView = findViewById(R.id.bus_5)
        val content_bus_5 = "Arrêt : Liberté TNB / Métro Charles de Gaulle"
        bus_5.setText(content_bus_5)


        //////////////
        /////////////

        val place_6: TextView = findViewById(R.id.place_6)
        val content_place_6 = "Grand Logis"
        place_6.setText(content_place_6)

        //////////////
        /////////////

        val address_6: TextView = findViewById(R.id.address_6)
        val content_address_6 = "10 Avenue du Général de Gaulle, 35170 Bruz"
        address_6.setText(content_address_6)

        //////////////
        /////////////

        val phone_6: TextView = findViewById(R.id.phone_6)
        val content_phone_6 = "02 99 05 30 62 / 02 99 05 30 60"
        phone_6.setText(content_phone_6)

        //////////////
        /////////////

        val bus_6: TextView = findViewById(R.id.bus_6)
        val content_bus_6 = "Arrêt : Bruz Centre"
        bus_6.setText(content_bus_6)

        //////////////
        /////////////

        val place_7: TextView = findViewById(R.id.place_7)
        val content_place_7 = "Musée de Bretagne"
        place_7.setText(content_place_7)

        //////////////
        /////////////

        val address_7: TextView = findViewById(R.id.address_7)
        val content_address_7 = "10 Cours des Alliés, 35000 Rennes"
        address_7.setText(content_address_7)

        //////////////
        /////////////

        val phone_7: TextView = findViewById(R.id.phone_7)
        val content_phone_7 = "02 23 40 66 00"
        phone_7.setText(content_phone_7)

        //////////////
        /////////////

        val bus_7: TextView = findViewById(R.id.bus_7)
        val content_bus_7 = "Arrêts : Charles de Gaulle, Magenta ou Gare"
        bus_7.setText(content_bus_7)

        //////////////
        /////////////

        val remark: TextView = findViewById(R.id.remark)
        val content_remark = "* Toutes les salles sont accessibles aux personnes à mobilité réduite."
        remark.setText(content_remark)
    }


    private fun setEat() {

        val opening_1: TextView = findViewById(R.id.opening_1)
        val content_opening_1 = "Ouvert les jeudis et vendredis de 12h à 14h le soir à partir de 18h."
        opening_1.setText(content_opening_1)

        //////////////
        //////////////

        val price_value_1: TextView = findViewById(R.id.price_value_1)
        val content_price_value_1 = "4€"
        price_value_1.setText(content_price_value_1)

        val price_content_1: TextView = findViewById(R.id.price_content_1)
        val content_price_content_1 = "Goûter (Tarif préférentiel)"
        price_content_1.setText(content_price_content_1)

        //////////////
        //////////////

        val days_1: TextView = findViewById(R.id.days_1)
        val content_days_1 = "Le mercredi, samedi et dimanche"
        days_1.setText(content_days_1)

        val days_2: TextView = findViewById(R.id.days_2)
        val content_days_2 = "Tous les soirs de 20h à 21h"
        days_2.setText(content_days_2)

        //////////////
        //////////////

        val price_value_2: TextView = findViewById(R.id.price_value_2)
        val content_price_value_2 = "11.50€"
        price_value_2.setText(content_price_value_2)

        val price_content_2: TextView = findViewById(R.id.price_content_2)
        val content_price_content_2 = "Happy Hour Gastronomique "
        price_content_2.setText(content_price_content_2)
    }


    private fun setOrganizer() {

        val organizer: TextView = findViewById(R.id.organizer)
        val content_organizer = "Organisé par"
        organizer.setText(content_organizer)

        /////////////
        /////////////

        val partners: TextView = findViewById(R.id.partners)
        val content_partners = "En partenariat avec"
        partners.setText(content_partners)


        /////////////
        /////////////

        val support: TextView = findViewById(R.id.support)
        val content_support = "Avec le soutien de"
        support.setText(content_support)

    }

}

