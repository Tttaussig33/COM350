package com.example.pa2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pa2.databinding.ActivityBookBinding

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var dbHelper: BookSQLiteHelper
    private var currentBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = BookSQLiteHelper(this)

        val bookId = intent.getIntExtra("BOOK_ID", -1)

        if (bookId == -1) {
            Toast.makeText(this, "Invalid book id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentBook = dbHelper.searchBook(bookId)

        if (currentBook == null) {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        showBookData(currentBook!!)

        binding.btnUpdateBook.setOnClickListener {
            updateBook()
        }

        binding.btnDeleteBook.setOnClickListener {
            deleteBook()
        }
    }

    private fun showBookData(book: Book) {
        binding.tvDisplayTitle.text = "Title: ${book.title}"
        binding.tvDisplayAuthor.text = "Author: ${book.author}"

        binding.etTitle.setText(book.title)
        binding.etAuthor.setText(book.author)
        binding.etImageId.setText(book.imageId)

        val resId = resources.getIdentifier(book.imageId, "drawable", packageName)
        if (resId != 0) {
            binding.ivBookLarge.setImageResource(resId)
        } else {
            binding.ivBookLarge.setImageResource(R.drawable.generic_book)
        }
    }

    private fun updateBook() {
        val book = currentBook ?: return

        val updatedTitle = binding.etTitle.text.toString().trim()
        val updatedAuthor = binding.etAuthor.text.toString().trim()
        val updatedImageId = binding.etImageId.text.toString().trim()

        if (updatedTitle.isEmpty() || updatedAuthor.isEmpty()) {
            Toast.makeText(this, "Title and author cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        book.title = updatedTitle
        book.author = updatedAuthor
        book.imageId = if (updatedImageId.isEmpty()) "generic_book" else updatedImageId

        dbHelper.updateBook(book)

        Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteBook() {
        val book = currentBook ?: return
        dbHelper.deleteBook(book)
        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show()
        finish()
    }
}