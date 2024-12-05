package com.arttseng.homeexam.stock.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock_Day_AllItem(
    val Change: String,
    val ClosingPrice: String,
    val Code: String,
    val HighestPrice: String,
    val LowestPrice: String,
    val Name: String,
    val OpeningPrice: String,
    val TradeValue: String,
    val TradeVolume: String,
    val Transaction: String
)