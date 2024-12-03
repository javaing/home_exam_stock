package com.arttseng.homeexam.airplane.tools

import android.util.Log
import com.arttseng.homeexam.airplane.datamodel.Airport


object Utils {

    fun getAirportName(airportData: List<Airport>, id: String): String {
        val result = airportData.filter { it.AirportID == id }
        return if(result.isEmpty() || result.get(0).AirportName.Zh_tw.isNullOrEmpty())  id else result.get(0).AirportName.Zh_tw!!
    }

    fun log(str: String) {
        Log.e("", "art $str")
    }

}