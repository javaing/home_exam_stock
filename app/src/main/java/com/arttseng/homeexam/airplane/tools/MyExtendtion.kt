package com.arttseng.homeexam.airplane.tools

import android.app.Activity
import android.widget.Toast
import kotlin.math.round


fun Activity.toast(str:String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}





