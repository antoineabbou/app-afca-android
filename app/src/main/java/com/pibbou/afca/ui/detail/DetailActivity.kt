package com.pibbou.afca.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pibbou.afca.App
import com.pibbou.afca.R
import android.view.View
import android.graphics.Color
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.*
import android.widget.CompoundButton.OnCheckedChangeListener
import com.pibbou.afca.repository.entity.Event
import com.squareup.picasso.Picasso


@RequiresApi(Build.VERSION_CODES.M)

/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    var isInList: Boolean = false

    private val onCheckedChanged: OnCheckedChangeListener

    // Favorite manager call
    val favoriteManager = App.sInstance.getFavoriteManager()


    init {
        onCheckedChanged = OnCheckedChangeListener { buttonView, _ ->
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
        val dataRepository = App.sInstance.getDataRepository()

        // Find the event, the place and the category in the list
        val event = dataRepository!!.findEventById(id)

        setButtonFavorite()
        setButtonBack()

        if (event != null) {
            setEventDatas(event)
            setPlaceDatas(event)
            setCategoryDatas(event)
            setButtonDatas(event)

            checkList(event)
        }
    }


    private fun checkList(event: Event) {
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)

        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === event.id
        }.count() > 0


        setToggleButton(toggle, event)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showToast(event: Event) {

        val toast = SuperActivityToast.create(this@DetailActivity, Style.TYPE_BUTTON)

        if (isInList) {
            favoriteManager?.removeFavorite(applicationContext, event)
            isInList = false
            toast.text = "ÉVÈNEMENT SUPPRIMÉ DES FAVORIS"
            toast.color = getColor(R.color.red)

        } else {
            favoriteManager?.addFavorite(applicationContext, event)
            isInList = true
            toast.color = getColor(R.color.green)
            toast.text = "ÉVÈNEMENT AJOUTÉ AUX FAVORIS"
        }


        toast
            .setProgressBarColor(Color.WHITE)
            .setDuration(Style.DURATION_VERY_SHORT)
            .setFrame(Style.FRAME_LOLLIPOP)
            .setAnimations(Style.ANIMATIONS_POP).show()
    }


    private fun setToggleButton(toggle:ToggleButton, event: Event) {

        toggle.isChecked = isInList != false

        with (toggle) {
            tag = event
            setOnCheckedChangeListener(onCheckedChanged)
        }
    }


    private fun setButtonBack() {
        val back: ImageButton = findViewById(R.id.back_button)
        back.setOnClickListener {
            finish()
        }
    }


    private fun setEventDatas(event: Event) {
        val eventTitle: TextView = findViewById(R.id.eventTitle)
        eventTitle.text = event.name?.toUpperCase()

        // Date
        val eventStart: TextView = findViewById(R.id.eventStart)
        val eventHour: TextView = findViewById(R.id.hour)
        val hour = android.text.format.DateFormat.format("HH:mm", event.startingDate) as String


        val dayOfTheWeek = android.text.format.DateFormat.format("EEEE", event.startingDate) as String // Thursday
        val day =  android.text.format.DateFormat.format("dd", event.startingDate) as String // 20
        val monthString =  android.text.format.DateFormat.format("MMM", event.startingDate) as String // Jun

        eventStart.text = getString(R.string.dateEvent, dayOfTheWeek.toUpperCase(), day, monthString.toUpperCase())
        eventHour.text = hour

        // Author
        val author: TextView = findViewById(R.id.author)

        if(event.author.isNullOrEmpty()) {
            author.text = "Auteur non communiqué"
        } else {
            author.text = event.author
        }

        // Duration
        val duration: TextView = findViewById(R.id.duration)
        duration.text = event.duration


        // Image
        val image: ImageView = findViewById(R.id.eventImage)
        image.scaleType = ImageView.ScaleType.CENTER_CROP

        Picasso.with(applicationContext)
                .load(event.image)
                .into(image)
    }


    private fun setPlaceDatas(event: Event) {
        // Place name
        val placeText: TextView = findViewById(R.id.placeText)
        placeText.text = event.place?.name

        // Place address
        val address: TextView = findViewById(R.id.address)
        address.text = event.place?.address
    }


    private fun setCategoryDatas(event: Event) {
        val categoryText: TextView = findViewById(R.id.categoryTitle)
        categoryText.text = event.category?.name?.toUpperCase()
        categoryText.setTextColor(Color.parseColor(event.category?.color))

        val colorCategory: View = findViewById(R.id.color_category)
        colorCategory.setBackgroundColor(Color.parseColor(event.category?.color))
    }


    private fun setButtonDatas(event: Event) {
        val button: Button = findViewById(R.id.button)
        button.setBackgroundColor(Color.parseColor(event.category?.color))
        button.setOnClickListener({ _ ->
            val uri = Uri.parse(event.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
    }


    fun setButtonFavorite() {
        val toggle = findViewById<ToggleButton>(R.id.buttonFav)
        toggle.text = null
        toggle.textOn = null
        toggle.textOff = null
    }
}