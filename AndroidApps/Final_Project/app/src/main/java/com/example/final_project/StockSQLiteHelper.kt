// Teddy Taussig, Melvin Barroso, Anthony Valladares

package com.example.final_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StockSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "stocks.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_STOCKS = "stocks"
        private const val COL_ID = "id"
        private const val COL_SYMBOL = "symbol"
        private const val COL_COMPANY_NAME = "companyName"
        private const val COL_LATEST_PRICE = "latestPrice"
        private const val COL_LATEST_CHANGE = "latestChange"
        private const val COL_LATEST_CHANGE_PERCENT = "latestChangePercent"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_STOCKS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_SYMBOL TEXT NOT NULL UNIQUE,
                $COL_COMPANY_NAME TEXT NOT NULL,
                $COL_LATEST_PRICE REAL,
                $COL_LATEST_CHANGE REAL,
                $COL_LATEST_CHANGE_PERCENT REAL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STOCKS")
        onCreate(db)
    }

    fun addStock(symbol: String, companyName: String): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_SYMBOL, symbol.uppercase())
            put(COL_COMPANY_NAME, companyName)
        }

        val result = db.insert(TABLE_STOCKS, null, values)
        db.close()

        return result != -1L
    }

    fun getAllStocks(): ArrayList<Stock> {
        val stocks = ArrayList<Stock>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_STOCKS ORDER BY $COL_SYMBOL ASC",
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val stock = Stock(
                        id = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                        symbol = it.getString(it.getColumnIndexOrThrow(COL_SYMBOL)),
                        companyName = it.getString(it.getColumnIndexOrThrow(COL_COMPANY_NAME)),
                        latestPrice = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_PRICE))) null
                        else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_PRICE)),
                        latestChange = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_CHANGE))) null
                        else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_CHANGE)),
                        latestChangePercent = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_CHANGE_PERCENT))) null
                        else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_CHANGE_PERCENT))
                    )

                    stocks.add(stock)
                } while (it.moveToNext())
            }
        }

        db.close()
        return stocks
    }

    fun getStock(symbol: String): Stock? {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_STOCKS WHERE $COL_SYMBOL = ?",
            arrayOf(symbol.uppercase())
        )

        var stock: Stock? = null

        cursor.use {
            if (it.moveToFirst()) {
                stock = Stock(
                    id = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                    symbol = it.getString(it.getColumnIndexOrThrow(COL_SYMBOL)),
                    companyName = it.getString(it.getColumnIndexOrThrow(COL_COMPANY_NAME)),
                    latestPrice = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_PRICE))) null
                    else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_PRICE)),
                    latestChange = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_CHANGE))) null
                    else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_CHANGE)),
                    latestChangePercent = if (it.isNull(it.getColumnIndexOrThrow(COL_LATEST_CHANGE_PERCENT))) null
                    else it.getDouble(it.getColumnIndexOrThrow(COL_LATEST_CHANGE_PERCENT))
                )
            }
        }

        db.close()
        return stock
    }

    fun updateStockData(
        symbol: String,
        latestPrice: Double,
        latestChange: Double,
        latestChangePercent: Double
    ): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_LATEST_PRICE, latestPrice)
            put(COL_LATEST_CHANGE, latestChange)
            put(COL_LATEST_CHANGE_PERCENT, latestChangePercent)
        }

        val rowsUpdated = db.update(
            TABLE_STOCKS,
            values,
            "$COL_SYMBOL = ?",
            arrayOf(symbol.uppercase())
        )

        db.close()
        return rowsUpdated > 0
    }

    fun deleteStock(symbol: String): Boolean {
        val db = writableDatabase

        val rowsDeleted = db.delete(
            TABLE_STOCKS,
            "$COL_SYMBOL = ?",
            arrayOf(symbol.uppercase())
        )

        db.close()
        return rowsDeleted > 0
    }
}