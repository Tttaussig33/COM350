package com.example.hw3  // <-- change to your package

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val toValue: TextView = findViewById(R.id.toValueTextView)
        val subjectValue: TextView = findViewById(R.id.subjectValueTextView)
        val messageValue: TextView = findViewById(R.id.messageValueTextView)

        val to = intent.getStringExtra(SenderActivity.EXTRA_TO) ?: ""
        val subject = intent.getStringExtra(SenderActivity.EXTRA_SUBJECT) ?: ""
        val message = intent.getStringExtra(SenderActivity.EXTRA_MESSAGE) ?: ""

        toValue.text = to
        subjectValue.text = subject
        messageValue.text = message
    }
}