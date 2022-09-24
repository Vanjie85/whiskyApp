package com.jogogojco.inlupp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var selectedWhisky = ""
    var selectedPrice = ""
    //var selectedCl = ""
    //var totalSum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create constant that holds the array lists from strings.xml:
        val whiskiesArray = resources.getStringArray(R.array.whiskies)
        val whiskyPriceArray = resources.getStringArray(R.array.whiskies_cl_price)
        //val amountOfClArray = resources.getStringArray(R.array.cl_option)
        //create variable to hold the textViews
        var whiskyAndPriceText = findViewById<TextView>(R.id.text_choose_whisky)
        //var clChoiceText = findViewById<TextView>(R.id.text_cl)
        //To find the spinner from the layout file, an instance of the Spinner object is needed
        val spinnerWhisky: Spinner = findViewById(R.id.spinner_whisky)
        //val spinnerCl: Spinner = findViewById(R.id.spinner_cl)
        //To connect the whisky spinner with the array data, an adapter is used:
        var whiskyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, whiskiesArray)
        //var clAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, amountOfClArray)
        //Following code is to make the spinner a drop-down menu
        whiskyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Connect the Spinner instance to the adapter
        spinnerWhisky.adapter = whiskyAdapter
        //spinnerCl.adapter = clAdapter

        //Following defines what happens when an item from the whisky array is selected
        spinnerWhisky.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedIndex = spinnerWhisky.getSelectedItemPosition() //get index position from array and store in selectedIndex
                if (selectedIndex > 0) {
                    selectedWhisky = whiskiesArray[selectedIndex]
                    selectedPrice = whiskyPriceArray[selectedIndex]
                }
                else {
                    selectedWhisky = ""
                    selectedPrice = ""
                }
                printWhisky(whiskyAndPriceText)
                //printCl(clChoiceText)
            }
        }

        /*spinnerCl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedIndex = spinnerCl.getSelectedItemPosition()
                if (selectedIndex > 0) {
                    selectedCl = amountOfClArray[selectedIndex]
                }
                else
                    selectedCl = ""

            }
        }*/

    }

    /*private fun printCl(updateText: TextView?) {
        if (updateText != null) {
            updateText.text = "Du har valt " + selectedCl
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun printWhisky(updateText: TextView) {
        if (selectedWhisky != "")
            updateText.text = getString(R.string.your_fav_whisky) +selectedWhisky +getString(R.string.cl_price) + selectedPrice+" kr/cl"
        else
            updateText.text = getString(R.string.choose_whisky)

    }
}