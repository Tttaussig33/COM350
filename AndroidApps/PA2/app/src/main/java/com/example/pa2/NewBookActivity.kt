package com.example.pa2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pa2.databinding.ActivityNewBookBinding

class NewBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewBookBinding
    private lateinit var dbHelper: BookSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = BookSQLiteHelper(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSaveBook.setOnClickListener {
            saveBook()
        }
    }

    private fun saveBook() {
        val title = binding.etNewTitle.text.toString().trim()
        val author = binding.etNewAuthor.text.toString().trim()
        val imageIdInput = binding.etNewImageId.text.toString().trim()

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Title and author cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val imageId = if (imageIdInput.isEmpty()) "generic_book" else imageIdInput

        val newBook = Book(
            title = title,
            imageId = imageId,
            author = author
        )

        dbHelper.addBook(newBook)

        Toast.makeText(this, "Book saved", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }
}