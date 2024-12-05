package com.arttseng.homeexam.stock.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttseng.homeexam.stock.datamodel.BWIBBU_ALLItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AVG_AllItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AllItem
import com.arttseng.homeexam.stock.tools.RetrofitFactory
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val apiClient = RetrofitFactory.WebAccess.API;

    val bwibbuData = MutableLiveData<List<BWIBBU_ALLItem>>();
    val stockDayAllData = MutableLiveData<List<Stock_Day_AllItem>>()
    val stockDayAvgAllData = MutableLiveData<List<Stock_Day_AVG_AllItem>>()

    init {
        getBWIBBU()
        getStockDayAll()
        getStockDayAVGAll()
    }

    fun getBWIBBU() {
        viewModelScope.launch {
            try {
                val response = apiClient.getBWIBBU()
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                bwibbuData.value = note ?: emptyList()
                Log.e("","art bwibbuData size:" + bwibbuData.value)
            } catch (e: Exception) {
                Log.e("", "art bwibbuData err:" + e.message)
            }
        }
    }

    fun getStockDayAll() {
        viewModelScope.launch {
            try {
                val response = apiClient.getDAYALL()
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                stockDayAllData.value = note ?: emptyList()
                Log.e("","art stockDayAllData size:" + stockDayAllData.value)
            } catch (e: Exception) {
                Log.e("", "art stockDayAllData err:" + e.message)
            }
        }
    }

    fun getStockDayAVGAll() {
        viewModelScope.launch {
            try {
                val response = apiClient.getDAYAVGALL()
                val note = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                stockDayAvgAllData.value = note ?: emptyList()
                Log.e("","art stockDayAvgAllData size:" + stockDayAvgAllData.value)
            } catch (e: Exception) {
                Log.e("", "art stockDayAvgAllData err:" + e.message)
            }
        }
    }
}