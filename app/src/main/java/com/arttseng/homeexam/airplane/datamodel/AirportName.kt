package com.arttseng.homeexam.airplane.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class I18nName(
    val En: String?,
    val Zh_tw: String?
)