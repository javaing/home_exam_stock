package com.arttseng.homeexam.stock.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock_Day_AVG_AllItem(
    val ClosingPrice: String,
    val Code: String,
    val MonthlyAveragePrice: String,
    val Name: String
)