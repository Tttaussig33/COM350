package com.example.pa2

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pa2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: BookSQLiteHelper
    private lateinit var adapter: BookAdapter

    private val bookActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            loadBooks()
        }

    private val newBookActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            loadBooks()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = BookSQLiteHelper(this)

        // Keep this only if your professor wants reseeding every launch
        dbHelper.resetAndSeedBooks()

        adapter = BookAdapter(emptyList()) { clickedBook ->
            val intent = Intent(this, BookActivity::class.java)
            intent.putExtra("BOOK_ID", clickedBook.id)
            bookActivityLauncher.launch(intent)
        }

        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBooks.adapter = adapter

        binding.btnAddNewBook.setOnClickListener {
            val intent = Intent(this, NewBookActivity::class.java)
            newBookActivityLauncher.launch(intent)
        }

        loadBooks()
    }

    private fun loadBooks() {
        val books = dbHelper.getAllBooks()
        adapter.updateData(books)
    }
}