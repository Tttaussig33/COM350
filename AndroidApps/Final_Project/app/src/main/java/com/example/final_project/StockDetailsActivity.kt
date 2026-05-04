package com.example.final_project

import androidx.core.content.ContextCompat
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.json.JSONObject

class StockDetailsActivity : AppCompatActivity() {

    private lateinit var dbHelper: StockSQLiteHelper

    private lateinit var textViewSymbol: TextView
    private lateinit var textViewCompanyName: TextView
    private lateinit var textViewLatestPrice: TextView
    private lateinit var textViewLatestChange: TextView
    private lateinit var textViewLatestChangePercent: TextView

    private lateinit var symbol: String
    private lateinit var companyName: String

    private val stockReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val error = intent?.getStringExtra(StockDataService.EXTRA_ERROR)
            val json = intent?.getStringExtra(StockDataService.EXTRA_JSON)

            if (error != null) {
                Toast.makeText(
                    this@StockDetailsActivity,
                    "API Error: $error",
                    Toast.LENGTH_LONG
                ).show()

                loadSavedData()
                return
            }

            if (json != null) {
                handleJsonResponse(json)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbarStockDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = StockSQLiteHelper(this)

        symbol = intent.getStringExtra("symbol") ?: ""
        companyName = intent.getStringExtra("companyName") ?: ""

        textViewSymbol = findViewById(R.id.textViewSymbol)
        textViewCompanyName = findViewById(R.id.textViewCompanyName)
        textViewLatestPrice = findViewById(R.id.textViewLatestPrice)
        textViewLatestChange = findViewById(R.id.textViewLatestChange)
        textViewLatestChangePercent = findViewById(R.id.textViewLatestChangePercent)

        textViewSymbol.text = "Symbol: $symbol"
        textViewCompanyName.text = "Company Name: $companyName"

        loadSavedData()
    }

    override fun onStart() {
        super.onStart()

        val filter = IntentFilter(StockDataService.ACTION_STOCK_RESULT)

        ContextCompat.registerReceiver(
            this,
            stockReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        fetchStockData()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(stockReceiver)
    }

    private fun fetchStockData() {
        Toast.makeText(this, "Refreshing stock data...", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, StockDataService::class.java).apply {
            action = StockDataService.ACTION_FETCH_STOCK
            putExtra(StockDataService.EXTRA_SYMBOL, symbol)
        }

        startService(intent)
    }

    private fun handleJsonResponse(json: String) {
        try {
            val jsonObject = JSONObject(json)

            val latestPrice = jsonObject.optDouble("close", Double.NaN)
            val latestChange = jsonObject.optDouble("change", Double.NaN)
            val latestChangePercent = jsonObject.optDouble("change_p", Double.NaN)

            if (
                latestPrice.isNaN() ||
                latestChange.isNaN() ||
                latestChangePercent.isNaN()
            ) {
                Toast.makeText(this, "Stock data missing from API response", Toast.LENGTH_LONG).show()
                loadSavedData()
                return
            }

            dbHelper.updateStockData(
                symbol,
                latestPrice,
                latestChange,
                latestChangePercent
            )

            displayStockData(
                latestPrice,
                latestChange,
                latestChangePercent
            )

        } catch (e: Exception) {
            Toast.makeText(this, "Error parsing stock data: ${e.message}", Toast.LENGTH_LONG).show()
            loadSavedData()
        }
    }

    private fun loadSavedData() {
        val stock = dbHelper.getStock(symbol)

        if (stock == null) {
            textViewLatestPrice.text = "Latest Price: Not available"
            textViewLatestChange.text = "Latest Change: Not available"
            textViewLatestChangePercent.text = "Percent Change: Not available"
            return
        }

        if (
            stock.latestPrice != null &&
            stock.latestChange != null &&
            stock.latestChangePercent != null
        ) {
            displayStockData(
                stock.latestPrice,
                stock.latestChange,
                stock.latestChangePercent
            )
        } else {
            textViewLatestPrice.text = "Latest Price: Not available"
            textViewLatestChange.text = "Latest Change: Not available"
            textViewLatestChangePercent.text = "Percent Change: Not available"
        }
    }

    private fun displayStockData(
        latestPrice: Double,
        latestChange: Double,
        latestChangePercent: Double
    ) {
        textViewLatestPrice.text = "Latest Price: $latestPrice"
        textViewLatestChange.text = "Latest Change: $latestChange"
        textViewLatestChangePercent.text = "Percent Change: $latestChangePercent%"

        val changeColor = if (latestChange >= 0) {
            Color.rgb(0, 150, 0)
        } else {
            Color.RED
        }

        val percentColor = if (latestChangePercent >= 0) {
            Color.rgb(0, 150, 0)
        } else {
            Color.RED
        }

        textViewLatestChange.setTextColor(changeColor)
        textViewLatestChangePercent.setTextColor(percentColor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stock_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionRefreshData -> {
                fetchStockData()
                true
            }

            R.id.actionVisitStockPage -> {
                val intent = Intent(this, StockPageActivity::class.java).apply {
                    putExtra("symbol", symbol)
                }
                startActivity(intent)
                true
            }

            R.id.actionDeleteStock -> {
                val success = dbHelper.deleteStock(symbol)

                if (success) {
                    Toast.makeText(this, "Stock deleted", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Could not delete stock", Toast.LENGTH_SHORT).show()
                }

                true
            }

            R.id.actionExitDetails -> {
                finish()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}