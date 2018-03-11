package com.example.pibbou.afca.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import java.text.SimpleDateFormat
import android.view.View
import android.graphics.Color
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.example.pibbou.afca.repository.FavoriteManager
import android.R.id.edit
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.*
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.repository.entity.Event
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.util.ArrayList


/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    var isInList: Boolean = false

    private val onCheckedChanged: CompoundButton.OnCheckedChangeListener

    // Favorite manager call
    val favoriteManager = App.sInstance!!.getFavoriteManager()

    init {
        onCheckedChanged = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val event = buttonView.tag as Event
            showToast(event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        val id = intent.getIntExtra("id", 0)

        // data repository call
        val dataRepository = App.sInstance!!.getDataRepository()

        /*****/

        // Find the event, the place and the category in the list
        val event = dataRepository!!.findEventById(id)
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)
        toggle.setTextOn("S")
        toggle.setTextOff("A")

        setDatas()

        if (event != null) {
            checkList(applicationContext, event)
        }

    }


    fun checkList(context: Context, event: Event) {
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)

        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === event.id
        }.count() > 0


        setButton(toggle, event)
    }



    fun showToast(event: Event) {

        val toast = SuperActivityToast.create(this@DetailActivity, Style.TYPE_BUTTON)

        if (isInList) {
            favoriteManager?.removeFavorite(applicationContext, event)
            isInList = false
            toast.setText("Evenement supprimé des favoris")
            toast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED))

        } else {
            favoriteManager?.addFavorite(applicationContext, event)
            isInList = true
            toast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE))
            toast.setText("Evenement ajouté favoris")
        }


        toast
            .setProgressBarColor(Color.WHITE)
            .setDuration(Style.DURATION_VERY_SHORT)
            .setFrame(Style.FRAME_LOLLIPOP)
            .setAnimations(Style.ANIMATIONS_POP).show()
    }



    fun setButton(toggle:ToggleButton, event: Event) {

        if(isInList == false) {

            // Init
            toggle.setText("Ajouter aux favoris")
            toggle.isChecked = false


        } else {

            // Init
            toggle.setText("Supprimer des favoris")
            toggle.isChecked = true

        }

        with (toggle) {
            tag = event
            setOnCheckedChangeListener(onCheckedChanged)
        }
    }


    fun setDatas() {
        val intent = intent
        val id = intent.getIntExtra("id", 0)

        // data repository call
        val dataRepository = App.sInstance!!.getDataRepository()


        /*****/

        // Find the event, the place and the category in the list
        val event = dataRepository!!.findEventById(id)
        val place = dataRepository!!.findPlaceById(id)
        val category = dataRepository!!.findCategoryById(id)


        // Loading datas : Title, excerpt, start, end, place and category

        ////////////////////
        ////////////////////
        ////////////// EVENT
        ////////////////////
        ////////////////////

        // Title
        val eventTitle: TextView = findViewById(R.id.eventTitle)
        eventTitle.setText(event?.name)


        // Excerpt
        val eventExcerpt: TextView = findViewById(R.id.eventExcerpt)
        eventExcerpt.setText(event?.excerpt)


        // Date
        val eventStart: TextView = findViewById(R.id.eventStart)
        val dayOfTheWeek = android.text.format.DateFormat.format("EEEE", event?.startingDate) as String // Thursday
        val day =  android.text.format.DateFormat.format("dd", event?.startingDate) as String // 20
        val monthString =  android.text.format.DateFormat.format("MMM", event?.startingDate) as String // Jun
        eventStart.setText(getString(R.string.dateEvent, dayOfTheWeek, day, monthString))

        val eventHour: TextView = findViewById(R.id.hour)
        val hour = android.text.format.DateFormat.format("HH:mm", event?.startingDate) as String
        eventHour.setText(hour)

        // Author
        val author: TextView = findViewById(R.id.author)
        author.setText(event?.author)

        val duration: TextView = findViewById(R.id.duration)
        duration.setText(event?.duration)


        // PLACE


        val placeText: TextView = findViewById(R.id.placeText)
        placeText.setText(place?.name)


        val categoryText: TextView = findViewById(R.id.categoryTitle)
        categoryText.setText(category?.name)

        // BUTTON

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener({ v ->
            val uri = Uri.parse(event?.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        val image: ImageView = findViewById(R.id.eventImage)
        image.setScaleType(ImageView.ScaleType.FIT_XY)

        Picasso.with(applicationContext)
                .load(event?.image)
                .into(image)


        // val toggle = findViewById<ToggleButton>(R.id.buttonFav)

    }
}