package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airport(
    val AirportCityName: I18nName,
    val AirportIATA: String,
    val AirportICAO: String,
    val AirportID: String,
    val AirportName: I18nName,
    val AirportNationality: String,
    //val AirportPosition: I18nName,
    val AuthorityID: String,
    val UpdateTime: String,
    val VersionID: Int
)