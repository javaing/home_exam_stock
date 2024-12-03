package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class Departures (
//    val data: List<Departure>
//)

@JsonClass(generateAdapter = true)
data class Departure(
    val AirlineID: String,
    val ArrivalAirportID: String,
    val DepartureAirportID: String,
    val DepartureRemark: String,
    val FlightDate: String,
    val FlightNumber: String,
    val Gate: String,
    val IsCargo: Boolean,
    val ScheduleDepartureTime: String,
    val ActualDepartureTime: String,
    val EstimatedDepartureTime: String,
    val Terminal: String,
    val UpdateTime: String
)