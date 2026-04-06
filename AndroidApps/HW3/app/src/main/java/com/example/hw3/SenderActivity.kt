package com.example.hw3   // <-- change to your package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TO = "EXTRA_TO"
        const val EXTRA_SUBJECT = "EXTRA_SUBJECT"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val toEdit: EditText = findViewById(R.id.toEditText)
        val subjectEdit: EditText = findViewById(R.id.subjectEditText)
        val messageEdit: EditText = findViewById(R.id.messageEditText)
        val sendBtn: Button = findViewById(R.id.sendButton)

        sendBtn.setOnClickListener {
            val intent = Intent(this, ViewerActivity::class.java).apply {
                putExtra(EXTRA_TO, toEdit.text.toString())
                putExtra(EXTRA_SUBJECT, subjectEdit.text.toString())
                putExtra(EXTRA_MESSAGE, messageEdit.text.toString())
            }
            startActivity(intent)
        }
    }
}