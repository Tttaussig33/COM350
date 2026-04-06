package com.example.hw1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun convertCurrency(view: View) {
        val dollarText = findViewById<EditText>(R.id.dollarText)
        val textView = findViewById<TextView>(R.id.textView)

        val dollarValue = dollarText.text.toString().toFloatOrNull()

        if (dollarValue != null) {
            val euroValue = dollarValue * 0.85f
            textView.text = String.format(Locale.US, "%.2f", euroValue)
        } else {
            textView.text = getString(R.string.no_value_string)
        }
    }
}
