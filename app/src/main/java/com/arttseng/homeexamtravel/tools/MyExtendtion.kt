package com.arttseng.homeexamtravel.tools

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arttseng.homeexamtravel.MyApplication
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
    lang: String,
    resourceId: Int
): String {
    val result: String
    val config = Configuration(this.resources.configuration)
    config.setLocale(Locale(lang))
    result = createConfigurationContext(config).getText(resourceId).toString()
    Log.e("", "art getLocaleString:"+result)
    return result
}



