package com.arttseng.homeexam.airplane.tools

import com.arttseng.homeexam.airplane.datamodel.Airport
import com.arttseng.homeexam.airplane.datamodel.ArrivalItem
import com.arttseng.homeexam.airplane.datamodel.Departure
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClientAirplane {

    @GET(Const.Departure)
    suspend fun getDeparture(
        @Path("iata") lang: String, @Query("\$format") format: String, @Query("\$top") top: Int, @Query("\$IsCargo") IsCargo: Boolean
        ): Response<List<Departure>>

    @GET(Const.Arrival)
    suspend fun getArrival(
            @Path("iata") lang: String, @Query("\$format") format: String, @Query("\$top") top: Int, @Query("\$IsCargo") IsCargo: Boolean
    ): Response<List<ArrivalItem>>

    @GET(Const.Airport)
    suspend fun getAirport(@Query("\$format") format: String): Response<List<Airport>>

}