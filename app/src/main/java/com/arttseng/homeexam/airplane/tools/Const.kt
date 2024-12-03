package com.arttseng.homeexam.airplane.tools

class Const {
    companion object {
        const val TDX_Base = "https://tdx.transportdata.tw/"
        const val TDX_Token = "auth/realms/TDXConnect/protocol/openid-connect/token"
        const val BaseUrl_Airplane = "https://tdx.transportdata.tw/api/basic/"
        const val Airport = "v2/Air/Airport"
        const val Departure = "v2/Air/FIDS/Airport/Departure/{iata}"
        const val Arrival = "v2/Air/FIDS/Airport/Arrival/{iata}"
        const val BaseUrl_Currency = "https://api.freecurrencyapi.com/"
        const val lastestCurrency = "v1/latest"
        const val currencyList = "USD,CNY,JPY,KRW,AUD,HKD,EUR"
        const val RecordSize = 20
        const val CountDown = 10
    }
}