package com.example.final_project

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NewStockActivity : AppCompatActivity() {

    private lateinit var dbHelper: StockSQLiteHelper
    private lateinit var editTextSymbol: EditText
    private lateinit var editTextCompanyName: EditText
    private lateinit var buttonSaveStock: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_stock)

        val toolbar = findViewById<Toolbar>(R.id.toolbarNewStock)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = StockSQLiteHelper(this)

        editTextSymbol = findViewById(R.id.editTextSymbol)
        editTextCompanyName = findViewById(R.id.editTextCompanyName)
        buttonSaveStock = findViewById(R.id.buttonSaveStock)

        buttonSaveStock.setOnClickListener {
            saveStock()
        }
    }

    private fun saveStock() {
        val symbol = editTextSymbol.text.toString().trim().uppercase()
        val companyName = editTextCompanyName.text.toString().trim()

        if (symbol.isEmpty() || companyName.isEmpty()) {
            Toast.makeText(this, "Please enter a stock symbol and company name", Toast.LENGTH_SHORT).show()
            return
        }

        val success = dbHelper.addStock(symbol, companyName)

        if (success) {
            Toast.makeText(this, "Stock added", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Stock already exists or could not be added", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}