// Teddy Taussig, Melvin Barroso, Anthony Valladares

package com.example.final_project

data class Stock(
    val id: Int = 0,
    val symbol: String,
    val companyName: String,
    val latestPrice: Double? = null,
    val latestChange: Double? = null,
    val latestChangePercent: Double? = null
)