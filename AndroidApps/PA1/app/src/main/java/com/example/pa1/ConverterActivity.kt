package com.example.pa1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ConverterActivity : AppCompatActivity() {

    private lateinit var tvConversionType: TextView
    private lateinit var etInputValue: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnConvert: Button
    private lateinit var btnStartOver: Button

    private var conversionType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        tvConversionType = findViewById(R.id.tvConversionType)
        etInputValue = findViewById(R.id.etInputValue)
        tvResult = findViewById(R.id.tvResult)
        btnConvert = findViewById(R.id.btnConvert)
        btnStartOver = findViewById(R.id.btnStartOver)

        conversionType = intent.getStringExtra("conversion_type") ?: "Kilogram(Kg) to Pounds(lb)"

        setupScreen()

        btnConvert.setOnClickListener {
            convertValue()
        }

        btnStartOver.setOnClickListener {
            finish()
        }
    }

    private fun setupScreen() {
        when (conversionType) {
            "Kilogram(Kg) to Pounds(lb)" -> {
                tvConversionType.text = "Converting measurements from Kilogram(Kg) to Pounds(lb)"
                etInputValue.hint = "Enter a measurement in Kilograms(Kg)"
            }

            "Pounds(lb) to Kilograms(Kg)" -> {
                tvConversionType.text = "Converting measurements from Pounds(lb) to Kilograms(Kg)"
                etInputValue.hint = "Enter a measurement in Pounds(lb)"
            }

            "Kilometers(Km) to Miles(mi)" -> {
                tvConversionType.text = "Converting measurements from Kilometers(Km) to Miles(mi)"
                etInputValue.hint = "Enter a measurement in Kilometers(Km)"
            }

            "Miles(mi) to Kilometers(Km)" -> {
                tvConversionType.text = "Converting measurements from Miles(mi) to Kilometers(Km)"
                etInputValue.hint = "Enter a measurement in Miles(mi)"
            }
        }
    }

    private fun convertValue() {
        val inputText = etInputValue.text.toString().trim()

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a value.", Toast.LENGTH_SHORT).show()
            return
        }

        val inputValue: Double
        try {
            inputValue = inputText.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show()
            return
        }

        val outputValue: Double
        val outputUnit: String

        when (conversionType) {
            "Kilogram(Kg) to Pounds(lb)" -> {
                outputValue = inputValue * 2.20462
                outputUnit = "Pounds(lb)"
            }

            "Pounds(lb) to Kilograms(Kg)" -> {
                outputValue = inputValue / 2.20462
                outputUnit = "Kilograms(Kg)"
            }

            "Kilometers(Km) to Miles(mi)" -> {
                outputValue = inputValue * 0.621371
                outputUnit = "Miles(mi)"
            }

            "Miles(mi) to Kilometers(Km)" -> {
                outputValue = inputValue / 0.621371
                outputUnit = "Kilometers(Km)"
            }

            else -> {
                Toast.makeText(this, "Unknown conversion type.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        tvResult.text = String.format(Locale.US, "Output: %.2f %s", outputValue, outputUnit)
    }
}