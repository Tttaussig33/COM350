package com.example.pa2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "library.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_BOOKS = "books"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE_ID = "image_id"
        const val COLUMN_AUTHOR = "author"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_BOOKS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_IMAGE_ID TEXT NOT NULL,
                $COLUMN_AUTHOR TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        onCreate(db)
    }

    fun addBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_IMAGE_ID, book.imageId)
            put(COLUMN_AUTHOR, book.author)
        }

        db.insert(TABLE_BOOKS, null, values)
        db.close()
    }

    fun searchBook(id: Int): Book? {
        val db = readableDatabase

        val cursor: Cursor = db.query(
            TABLE_BOOKS,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE_ID, COLUMN_AUTHOR),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var book: Book? = null

        if (cursor.moveToFirst()) {
            book = Book(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                imageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID)),
                author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            )
        }

        cursor.close()
        db.close()
        return book
    }

    fun getAllBooks(): MutableList<Book> {
        val books = mutableListOf<Book>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_BOOKS,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_IMAGE_ID, COLUMN_AUTHOR),
            null,
            null,
            null,
            null,
            "$COLUMN_TITLE ASC"
        )

        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    imageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_ID)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
                )
                books.add(book)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return books
    }

    fun updateBook(book: Book) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_IMAGE_ID, book.imageId)
            put(COLUMN_AUTHOR, book.author)
        }

        db.update(
            TABLE_BOOKS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(book.id.toString())
        )

        db.close()
    }

    fun deleteBook(book: Book) {
        val db = writableDatabase
        db.delete(
            TABLE_BOOKS,
            "$COLUMN_ID = ?",
            arrayOf(book.id.toString())
        )
        db.close()
    }

    fun resetAndSeedBooks() {
        val db = writableDatabase
        onUpgrade(db, 1, 2)

        addBook(Book(title = "The Great Gatsby", imageId = "great_gatsby", author = "F. Scott Fitzgerald"))
        addBook(Book(title = "Anna Karenina", imageId = "anna_karenina", author = "Leo Tolstoy"))
        addBook(Book(title = "The Grapes of Wrath", imageId = "grapes_of_wrath", author = "John Steinbeck"))
        addBook(Book(title = "Invisible Man", imageId = "invisible_man", author = "Ralph Ellison"))
        addBook(Book(title = "Gone with the Wind", imageId = "gone_with_the_wind", author = "Margaret Mitchell"))
        addBook(Book(title = "Pride and Prejudice", imageId = "pride_and_prejudice", author = "Jane Austen"))
        addBook(Book(title = "Sense and Sensibility", imageId = "sense_and_sensibility", author = "Jane Austen"))
        addBook(Book(title = "Mansfield Park", imageId = "mansfield_park", author = "Jane Austen"))
        addBook(Book(title = "War and Peace", imageId = "generic_book", author = "Leo Tolstoy"))
        addBook(Book(title = "Mrs Dalloway", imageId = "generic_book", author = "Virginia Woolf"))
        addBook(Book(title = "The Color Purple", imageId = "generic_book", author = "Alice Walker"))
        addBook(Book(title = "The Waves", imageId = "generic_book", author = "Virginia Woolf"))
        addBook(Book(title = "Beloved", imageId = "generic_book", author = "Toni Morrison"))
    }
}