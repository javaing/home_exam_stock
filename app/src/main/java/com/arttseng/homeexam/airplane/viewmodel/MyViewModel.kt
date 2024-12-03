package com.arttseng.homeexam.airplane.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttseng.homeexam.airplane.MyApplication
import com.arttseng.homeexam.airplane.datamodel.Airport
import com.arttseng.homeexam.airplane.datamodel.ArrivalItem
import com.arttseng.homeexam.airplane.datamodel.Currencies
import com.arttseng.homeexam.airplane.datamodel.Departure
import com.arttseng.homeexam.airplane.tools.Const
import com.arttseng.homeexam.airplane.tools.RetrofitFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val apiClient = RetrofitFactory.WebAccess.API_Airpane;
    private val apiCurrency = RetrofitFactory.WebAccess.APICurrency
    //private var userPickLanguage:String

    val airportData = MutableLiveData<List<Airport>>();
    val departureData = MutableLiveData<List<Departure>>()
    val arrivalPlaneData = MutableLiveData<List<ArrivalItem>>()
    val isNeedReload = MutableLiveData(false)
    val currenciesData = MutableLiveData<Currencies?>();

    private val testStr = """
[
  {
    "AirportID": "AAN",
    "AirportName": {
      "Zh_tw": "艾因國際機場",
      "En": "AL AIN"
    },
    "AirportIATA": "AAN",
    "AirportICAO": "OMAL",
    "AirportPosition": {},
    "AirportCityName": {},
    "AirportNationality": "AE",
    "AuthorityID": "CAA",
    "UpdateTime": "2024-12-01T09:12:09+08:00",
    "VersionID": 1542
  }
]
    """.trimIndent()

    private val dummyDeparture = """
        [
  {
    "FlightDate": "2024-12-01",
    "FlightNumber": "7016",
    "AirlineID": "DA",
    "DepartureAirportID": "CMJ",
    "ArrivalAirportID": "MZG",
    "ScheduleDepartureTime": "2024-12-01T14:25",
    "ActualDepartureTime": "2024-12-01T14:19",
    "DepartureRemark": "已飛",
    "Terminal": "",
    "Gate": "",
    "IsCargo": false,
    "UpdateTime": "2024-12-01T20:08:16+08:00"
  }
]
    """.trimIndent()

    private val dummyArrival = """
        [
          {
            "FlightDate": "2024-12-01",
            "FlightNumber": "885",
            "AirlineID": "ZE",
            "DepartureAirportID": "CJU",
            "ArrivalAirportID": "TPE",
            "ScheduleArrivalTime": "2024-12-01T00:05",
            "ActualArrivalTime": "2024-12-01T00:00",
            "EstimatedArrivalTime": "2024-12-01T00:00",
            "ArrivalRemark": "已到ARRIVED",
            "Terminal": "1",
            "Gate": " A7",
            "IsCargo": false,
            "AcType": "",
            "BaggageClaim": "03",
            "UpdateTime": "2024-12-02T16:30:09+08:00"
          }
        ]
    """.trimIndent()

//    private fun genTestData(): List<Airport>? {
//        val moshi = Moshi.Builder().build()
//        val adapter = moshi.adapter<List<Airport>>(Airport::class.java)
//        return adapter.fromJson(testStr)
//    }

//    private fun genDummyDeparture(): List<Departure>? {
//        val moshi = Moshi.Builder().build()
//        val adapter = moshi.adapter<List<Departure>>(Departure::class.java)
//        return adapter.fromJson(dummyDeparture)
//    }

//    private fun genDummyArrival(): List<ArrivalItem>? {
//        val moshi = Moshi.Builder().build()
//        val adapter = moshi.adapter<List<ArrivalItem>>(ArrivalItem::class.java)
//        return adapter.fromJson(dummyArrival)
//    }


    init {
        getAirport()
    }

    fun getCurrencies(base: String) {
        viewModelScope.launch {
            try {
                val response = apiCurrency.getLastestCurrency(MyApplication.currencyApikey, Const.currencyList, base_currency = base)
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                currenciesData.value = note
                Log.e("","art currenciesData size:" + currenciesData.value)
            } catch (e: Exception) {
                Log.e("", "art currenciesData err:" + e.message)
            }
        }
    }

    fun setReload(isNeed: Boolean) {
        isNeedReload.value = isNeed;
    }

    fun getAirport() {
        viewModelScope.launch {
            try {
                val response = apiClient.getAirport("JSON")
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    emptyList()
                    //genTestData()
                }
                airportData.value = note ?: emptyList()
                Log.e("","art airportData size:" + airportData.value?.size)
            } catch (e: Exception) {
                Log.e("", "art airportData err:" + e.message)
            }
        }
    }

    fun getDeparture() {

        if(airportData.value?.isEmpty() == true) getAirport()

        viewModelScope.launch {
            val response = apiClient.getDeparture("TPE","JSON", Const.RecordSize, false)
            val note = if (response.isSuccessful) {
                response.body()
            } else {
                emptyList()
            }
            departureData.value = note ?: emptyList()
        }
    }

    fun getArrival() {

        if(airportData.value?.isEmpty() == true) getAirport()

        viewModelScope.launch {
            val response = apiClient.getArrival("TPE","JSON", Const.RecordSize, false)
            val note = if (response.isSuccessful) {
                response.body()
            } else {
                //genDummyArrival()
                emptyList()
            }
            arrivalPlaneData.value = note ?: emptyList()
        }
    }

}