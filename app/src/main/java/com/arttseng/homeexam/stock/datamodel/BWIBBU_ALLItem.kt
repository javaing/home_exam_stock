package com.arttseng.homeexam.stock.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BWIBBU_ALLItem(
    val Code: String,
    val DividendYield: String,
    val Name: String,
    val PBratio: String,
    val PEratio: String
)