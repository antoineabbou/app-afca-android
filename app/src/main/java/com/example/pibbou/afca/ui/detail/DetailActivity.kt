package com.example.pibbou.afca.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import java.text.SimpleDateFormat
import android.view.View
import android.widget.Button
import android.graphics.Color
import android.widget.ToggleButton
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils
import com.example.pibbou.afca.repository.FavoriteManager
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pibbou.afca.repository.entity.Event
import java.util.ArrayList


/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    val favoriteManager = FavoriteManager()

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
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)
        toggle.setTextOn("Supprimer des favoris")
        toggle.setTextOff("Ajouter aux favoris")

        setDatas()

        if (event != null) {
            checkList(applicationContext, event)
        }

    }

    fun checkList(context: Context, event: Event) {
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)
        toggle.setTextOn("Supprimer des favoris")
        toggle.setTextOff("Ajouter aux favoris")

        var favorites: MutableList<Event>? = favoriteManager.getFavorites(context)

        if (favorites == null ) {
            favorites = ArrayList<Event>()
        }


        var isInList = favorites.filter {
            it.id === event.id
        }.count() > 0

        if(isInList == false) {
            toggle.setText("Ajouter aux favoris")
            toggle.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    SuperActivityToast.create(this@DetailActivity, Style.TYPE_BUTTON)
                            // .setButtonText("MASQUER")
                            .setProgressBarColor(Color.WHITE)
                            .setText("Evenement ajouté aux favoris")
                            .setDuration(Style.DURATION_VERY_SHORT)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE))
                            // .setOnButtonClickListener("good_tag_name", null, onButtonClickListener)
                            .setAnimations(Style.ANIMATIONS_POP).show()


                    // addToFavorite()

                    if (event != null) {
                        favoriteManager.addFavorite(applicationContext, event)
                    }

                    //favoriteManager.getFavorites(applicationContext)
                }


            })
        } else {
            toggle.setText("Supprimer des favoris")
            toggle.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {

                    SuperActivityToast.create(this@DetailActivity, Style.TYPE_BUTTON)
                        // .setButtonText("MASQUER")
                        .setProgressBarColor(Color.WHITE)
                        .setText("Evenement supprimé des favoris")
                        .setDuration(Style.DURATION_VERY_SHORT)
                        .setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED))
                        // .setOnButtonClickListener("good_tag_name", null, onButtonClickListener)
                        .setAnimations(Style.ANIMATIONS_POP).show()

                    // addToFavorite()

                    if (event != null) {
                        favoriteManager.removeFavorite(applicationContext, event)
                    }


                    //favoriteManager.getFavorites(applicationContext)
                }


            })
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


        // EVENT

        // Title
        val eventTitle: TextView = findViewById(R.id.eventTitle)
        eventTitle.setText(event?.name)


        // Excerpt
        val eventExcerpt: TextView = findViewById(R.id.eventExcerpt)
        eventExcerpt.setText(event?.excerpt)


        // Start
        val eventStart: TextView = findViewById(R.id.eventStart)
        val dateStart = event!!.startingDate
        val sdf_start = SimpleDateFormat("MMM MM dd, yyyy h:mm a") // Adapting format
        val dateStringStart = sdf_start.format(dateStart)
        eventStart.setText(dateStringStart)


        // End
        val eventEnd: TextView = findViewById(R.id.eventEnd)
        val dateEnd = event!!.endingDate
        val sdf_end = SimpleDateFormat("MMM MM dd, yyyy h:mm a") // Adapting format
        val dateStringEnd = sdf_end.format(dateEnd)
        eventEnd.setText(dateStringEnd)


        // PLACE


        val placeText: TextView = findViewById(R.id.placeText)
        placeText.setText(place?.name)


        val categoryText: TextView = findViewById(R.id.categoryTitle)
        categoryText.setText(category?.name)


        // val toggle = findViewById<ToggleButton>(R.id.buttonFav)

    }
}