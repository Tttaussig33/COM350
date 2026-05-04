// Teddy Taussig, Melvin Barroso, Anthony Valladares

package com.example.final_project

import android.util.Log
import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.net.HttpURLConnection
import java.net.URL

class StockDataService : Service() {

    companion object {
        const val ACTION_FETCH_STOCK = "com.example.final_project.FETCH_STOCK"
        const val ACTION_STOCK_RESULT = "com.example.final_project.STOCK_RESULT"

        const val EXTRA_SYMBOL = "symbol"
        const val EXTRA_JSON = "json"
        const val EXTRA_ERROR = "error"

        private const val API_KEY = "demo"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val symbol = intent?.getStringExtra(EXTRA_SYMBOL)

        if (symbol == null) {
            stopSelf(startId)
            return START_NOT_STICKY
        }

        Thread {
            try {
                val apiSymbol = if (symbol.contains(".")) symbol else "$symbol.US"

                val urlString =
                    "https://eodhd.com/api/real-time/$apiSymbol?api_token=$API_KEY&fmt=json"

                Log.d("STOCK_API_URL", urlString)

                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val responseCode = connection.responseCode

                val response = if (responseCode in 200..299) {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    connection.errorStream?.bufferedReader()?.use { it.readText() }
                        ?: "No error response"
                }

                Log.d("STOCK_API_CODE", responseCode.toString())
                Log.d("STOCK_API_RESPONSE", response)

                if (responseCode in 200..299) {
                    val resultIntent = Intent(ACTION_STOCK_RESULT).apply {
                        setPackage(packageName)
                        putExtra(EXTRA_SYMBOL, symbol)
                        putExtra(EXTRA_JSON, response)
                    }

                    sendBroadcast(resultIntent)
                } else {
                    val errorIntent = Intent(ACTION_STOCK_RESULT).apply {
                        putExtra(EXTRA_SYMBOL, symbol)
                        putExtra(EXTRA_ERROR, response)
                    }

                    sendBroadcast(errorIntent)
                }

                connection.disconnect()

            } catch (e: Exception) {
                Log.e("STOCK_API_ERROR", e.message ?: "Unknown error")

                val errorIntent = Intent(ACTION_STOCK_RESULT).apply {
                    putExtra(EXTRA_SYMBOL, symbol)
                    putExtra(EXTRA_ERROR, e.message ?: "Unknown error")
                }

                sendBroadcast(errorIntent)
            } finally {
                stopSelf(startId)
            }
        }.start()

        return START_NOT_STICKY
    }
}