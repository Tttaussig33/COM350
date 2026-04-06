package com.example.pa1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroupConversions: RadioGroup
    private lateinit var btnLaunchConverter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroupConversions = findViewById(R.id.radioGroupConversions)
        btnLaunchConverter = findViewById(R.id.btnLaunchConverter)

        btnLaunchConverter.setOnClickListener {
            val selectedId = radioGroupConversions.checkedRadioButtonId

            if (selectedId == -1) {
                Toast.makeText(
                    this,
                    "Please select a conversion type.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedConversion = selectedRadioButton.text.toString()

            val intent = Intent(this, ConverterActivity::class.java)
            intent.putExtra("conversion_type", selectedConversion)
            startActivity(intent)
        }
    }
}