package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyData(
    val CNY: Double,
    val EUR: Double,
    val HKD: Double,
    val JPY: Double,
    val KRW: Double,
    val AUD: Double,
    val USD: Double
)

data class CurrencyItem(
    val name: String,
    val rate: Double
)