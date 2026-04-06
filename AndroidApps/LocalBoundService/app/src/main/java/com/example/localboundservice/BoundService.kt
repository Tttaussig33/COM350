package com.example.localboundservice

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import java.util.Date
import java.util.Locale

class BoundService : Service() {
    private val myBinder = MyLocalBinder()
    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }

    fun getCurrentTime(): String {
        val dateformat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
        return dateformat.format(Date())

    }

    inner class MyLocalBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }
    }
}