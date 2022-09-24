package com.jogogojco.inlupp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var selectedWhisky = ""
    var selectedPrice = ""
    var selectedCl = ""
    var pricePerCl = 0
    var nrOfCl = 0
    var totalSum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create constant that holds the array lists from strings.xml:
        val whiskiesArray = resources.getStringArray(R.array.whiskies)
        val whiskyPriceArray = resources.getStringArray(R.array.whiskies_cl_price)
        val amountOfClArray = resources.getStringArray(R.array.cl_option)
        //create variable to hold the textViews
        var whiskyAndPriceText = findViewById<TextView>(R.id.text_choose_whisky)
        var clChoiceText = findViewById<TextView>(R.id.text_cl)
        //To find the spinner from the layout file, an instance of the Spinner object is needed
        val spinnerWhisky: Spinner = findViewById(R.id.spinner_whisky)
        val spinnerCl: Spinner = findViewById(R.id.spinner_cl)
        //To connect the whisky spinner with the array data, an adapter is used:
        var whiskyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, whiskiesArray)
        var clAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, amountOfClArray)
        //Following code is to make the spinner a drop-down menu
        whiskyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Connect the Spinner instance to the adapter
        spinnerWhisky.adapter = whiskyAdapter
        spinnerCl.adapter = clAdapter

        //Following defines what happens when an item from the whisky array is selected
        spinnerWhisky.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedIndex = spinnerWhisky.getSelectedItemPosition() //get index position from array and store in var selectedIndex
                if (selectedIndex > 0) {
                    selectedWhisky = whiskiesArray[selectedIndex]
                    selectedPrice = whiskyPriceArray[selectedIndex]
                    pricePerCl = selectedPrice.toInt() //convert selected price to int for calculations
                    if (selectedCl != "")
                        resetSpinner() //reset cl spinner in case you already choose a whiskey and want another
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

    }
        private fun printWhisky(updateText: TextView) {
        totalSum = pricePerCl * nrOfCl
        if (selectedWhisky != "")
            updateText.text = getString(R.string.your_choice_is) +selectedWhisky +getString(R.string.cl_price) + selectedPrice+" kr/cl"
        else
            updateText.text = getString(R.string.choose_whisky)

    }

    private fun printCl(updateText: TextView?) {
        totalSum = pricePerCl * nrOfCl
        if (selectedCl != "") {
            if (updateText != null) {
                if (selectedWhisky == ""){
                    Toast.makeText(this@MainActivity, "Choose whisky first!", Toast.LENGTH_SHORT).show()
                    resetSpinner()}
                else
                    updateText.text = getString(R.string.your_choice_is) + selectedCl + "\n" + getString(R.string.tot_sum) + totalSum + " kr"
            }
        }
        else
            if (updateText != null) {
                updateText.text = ""
            }
    }



    private fun resetSpinner(){
        val spinnerCl: Spinner = findViewById(R.id.spinner_cl)
        spinnerCl.setSelection(0)
    }
}