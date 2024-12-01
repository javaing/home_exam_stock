package com.arttseng.homeexam.airplane.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arttseng.homeexam.airplane.MyApplication
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.Locale

fun ImageView.load(url: String?) {
    Glide.with(MyApplication.instance())
        .load(url)
        .into(this)
}

fun ImageView.roundImage(url: String?) {
    Glide.with(MyApplication.instance())
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun Activity.toast(str:String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Fragment.setActivityTitle(@StringRes id: Int) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = getString(id)
}

fun Fragment.setActivityTitle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

fun Context.getLocaleString(
    lang: String?,
    resourceId: Int
): String {

    var myLang = lang
    if(myLang.isNullOrBlank()) myLang = "zh-tw"
    Utils.log("lang $myLang")
    val pos = myLang.indexOf("-")
    val locale = if(pos>0) {
        Locale(myLang.substring(0, pos), myLang.substring(pos+1))
    } else {
        Locale(myLang)
    }

    val result: String
    val config = Configuration(this.resources.configuration)
    config.setLocale(locale)
    result = createConfigurationContext(config).getText(resourceId).toString()
    Utils.log("getLocaleString:$result")
    return result
}

fun Activity.openLink(urls: String) {
    val uris = Uri.parse(urls)
    val intents = Intent(Intent.ACTION_VIEW, uris)
    val b = Bundle()
    b.putBoolean("new_window", true)
    intents.putExtras(b)
    startActivity(intents)
}



