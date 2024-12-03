package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currencies(
    val `data`: CurrencyData?
)