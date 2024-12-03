package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArrivalItem(
    val AcType: String,
    val ActualArrivalTime: String,
    val AirlineID: String,
    val ArrivalAirportID: String,
    val ArrivalRemark: String,
    val BaggageClaim: String,
    val DepartureAirportID: String,
    val EstimatedArrivalTime: String,
    val FlightDate: String,
    val FlightNumber: String,
    val Gate: String,
    val IsCargo: Boolean,
    val ScheduleArrivalTime: String,
    val Terminal: String,
    val UpdateTime: String
)