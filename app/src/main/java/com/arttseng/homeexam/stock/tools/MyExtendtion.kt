package com.arttseng.homeexam.stock.tools

import android.app.Activity
import android.widget.Toast
import java.util.Locale
import kotlin.math.round


fun Activity.toast(str:String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun String.toWan(): String {
    if (this.isEmpty()) return ""
    val value = this.toDouble() / 10000
    return value.round(1).toString() + "萬"
}

fun String.format(): String {
    if (this.isEmpty()) return ""
    return String.format(Locale.TAIWAN ,"%,d", this.toInt())
}

fun String.removeDecimalZero(): String {
    if (this.isEmpty()) return ""
    return this.toDouble().toString()
}





