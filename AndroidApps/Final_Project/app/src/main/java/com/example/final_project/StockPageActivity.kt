package com.example.final_project

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StockPageActivity : AppCompatActivity() {

    private lateinit var webViewStockPage: WebView
    private lateinit var symbol: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_page)

        val toolbar = findViewById<Toolbar>(R.id.toolbarStockPage)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        symbol = intent.getStringExtra("symbol") ?: ""

        val marketWatchSymbol = symbol.substringBefore(".")
        val url = "https://www.marketwatch.com/investing/stock/$marketWatchSymbol"

        webViewStockPage = findViewById(R.id.webViewStockPage)
        webViewStockPage.webViewClient = WebViewClient()
        webViewStockPage.settings.javaScriptEnabled = true

        webViewStockPage.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_stock_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionExitStockPage -> {
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