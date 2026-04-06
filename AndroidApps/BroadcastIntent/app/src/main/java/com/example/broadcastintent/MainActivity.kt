package com.example.broadcastintent

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var receiver: BroadcastReceiver? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configureReceiver()
    }

    fun sendBroadcast(view: View) {
        val intent = Intent()
        intent.action = "com.example.broadcastintent"
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        sendBroadcast(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("com.example.broadcastintent")
        filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED")
        filter.addAction("android.intent.action.ACTION_POWER_CONNECTED")
        receiver = MyReceiver()
        registerReceiver(receiver, filter, RECEIVER_EXPORTED)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}