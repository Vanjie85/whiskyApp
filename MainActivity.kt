package com.jogogojco.inlupp2

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var selectedWhisky = ""
    var selectedPrice = ""
    var selectedCl = ""
    var selectedArtist = ""
    var pricePerCl = 0
    var nrOfCl = 0
    var totalSum = 0
    //var artistImage: ImageView = findViewById(R.id.image_artist)
    //var artistImage:ImageView = findViewById(R.id.image_artist)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create constant that holds the array lists from strings.xml:
        val whiskiesArray = resources.getStringArray(R.array.whiskies)
        val whiskyPriceArray = resources.getStringArray(R.array.whiskies_cl_price)
        val amountOfClArray = resources.getStringArray(R.array.cl_option)
        val artistsArray = resources.getStringArray(R.array.artists)
        val artistsImageArray = resources.obtainTypedArray(R.array.artist_pics)
        //create variable to hold the textViews
        var whiskyAndPriceText = findViewById<TextView>(R.id.text_choose_whisky)
        var clChoiceText = findViewById<TextView>(R.id.text_cl)
        var artistText = findViewById<TextView>(R.id.text_artist)
        //To find the spinner from the layout file, an instance of the Spinner object is needed
        val spinnerWhisky: Spinner = findViewById(R.id.spinner_whisky)
        val spinnerCl: Spinner = findViewById(R.id.spinner_cl)
        val spinnerArtist: Spinner = findViewById(R.id.spinner_artist)
        //To connect the whisky spinner with the array data, an adapter is used:
        var whiskyAdapter = ArrayAdapter(this, R.layout.spinner_item, whiskiesArray)
        var clAdapter = ArrayAdapter(this, R.layout.spinner_item, amountOfClArray)
        var artistAdapter = ArrayAdapter(this, R.layout.spinner_item, artistsArray)
        //Following code is to make the spinner a drop-down menu
        whiskyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Connect the Spinner instance to the adapter
        spinnerWhisky.adapter = whiskyAdapter
        spinnerCl.adapter = clAdapter
        spinnerArtist.adapter = artistAdapter



        //Following defines what happens when an item from the spinner/arrays are selected
        spinnerWhisky.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // TODO: If whiskey is selected and also cl and then you choose select whiskey -> reset cl spinner 
                var selectedIndex = spinnerWhisky.getSelectedItemPosition() //get index position from array and store in var selectedIndex
                if (selectedIndex > 0) {
                    selectedWhisky = whiskiesArray[selectedIndex]
                    selectedPrice = whiskyPriceArray[selectedIndex]
                    pricePerCl = selectedPrice.toInt() //convert selected price to int for calculations
                    if (selectedCl != "")
                        resetSpinner() //reset cl spinner in case you already choose a whiskey and want another
                    setWhiskyImage(selectedIndex)
                }
                else {
                    selectedWhisky = ""
                    selectedPrice = ""
                }
                printWhisky(whiskyAndPriceText)
            }
        }

        spinnerCl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedIndex = spinnerCl.getSelectedItemPosition()
                if (selectedIndex > 0) {
                    selectedCl = amountOfClArray[selectedIndex]
                    nrOfCl = selectedCl.substring(0,1).toInt()
                }
                else
                    selectedCl = ""
                printCl(clChoiceText)

            }
        }

        spinnerArtist.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedIndex = spinnerArtist.getSelectedItemPosition()
                if (selectedIndex > 0){
                    selectedArtist = artistsArray[selectedIndex]
                    setArtistImage(selectedIndex)

                }
                else
                    selectedArtist = ""
                printArtist(artistText)
            }
        }

    }

    private fun setWhiskyImage(selectedWhisky:Int){
        var whiskyImage: ImageView = findViewById(R.id.image_whisky)
        when(selectedWhisky){
            // TODO: when 0 set mock pic
            0 -> whiskyImage.setImageResource(R.drawable.whisky1)
            1 -> whiskyImage.setImageResource(R.drawable.lagavulin)
            2 -> whiskyImage.setImageResource(R.drawable.oban)
            3 -> whiskyImage.setImageResource(R.drawable.talisker)
            4 -> whiskyImage.setImageResource(R.drawable.ardbeg)
            5 -> whiskyImage.setImageResource(R.drawable.glenlivet)
            6 -> whiskyImage.setImageResource(R.drawable.bunnahabhain)
            7 -> whiskyImage.setImageResource(R.drawable.laphroaig)
            8 -> whiskyImage.setImageResource(R.drawable.coal)
            9 -> whiskyImage.setImageResource(R.drawable.auchentoshan)
        }

    }
    private fun setArtistImage(selectedArtist:Int){
        var artistImage: ImageView = findViewById(R.id.image_artist)
        when(selectedArtist){
            0 -> artistImage.setImageResource(R.drawable.whisky1)
            1 -> artistImage.setImageResource(R.drawable.abba)
            2 -> artistImage.setImageResource(R.drawable.radiohead)
        }
    }
    private fun printArtist(updateText: TextView) {
        if (selectedArtist != "")
            updateText.text = selectedArtist // TODO: välj annan text 
        else
            updateText.text = ""
    }

    private fun printWhisky(updateText: TextView) {
        totalSum = pricePerCl * nrOfCl
        if (selectedWhisky != "")
            updateText.text = getString(R.string.your_choice_is) +selectedWhisky +getString(R.string.cl_price) + selectedPrice+" kr/cl"
        else
            updateText.text = getString(R.string.choose_whisky)

    }

    private fun printCl(updateText: TextView) {
        totalSum = pricePerCl * nrOfCl
        if (selectedCl != "") {
            if (selectedWhisky == ""){
                Toast.makeText(this@MainActivity, "Choose whisky first!", Toast.LENGTH_SHORT).show()
                resetSpinner()}
            else
                updateText.text = getString(R.string.your_choice_is) + selectedCl + "\n" + getString(R.string.tot_sum) + totalSum + " kr"
        }
        else
            updateText.text = ""

    }

    private fun resetSpinner(){
        val spinnerCl: Spinner = findViewById(R.id.spinner_cl)
        spinnerCl.setSelection(0)
    }
}