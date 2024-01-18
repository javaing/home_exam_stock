// To parse the JSON, install Klaxon and do:
//
//   val welcome10 = Welcome10.fromJson(jsonString)

package com.arttseng.homeexamtravel.datamodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attractions (
  val total: Long,
  val data: List<Attraction>
) {
//    public fun toJson() = klaxon.toJsonString(this)
//
//    companion object {
//        public fun fromJson(json: String) = klaxon.parse<Welcome8>(json)
//    }
}

@JsonClass(generateAdapter = true)
data class Attraction (
  val id: Long,
  val name: String,

  @Json(name = "name_zh")
  val nameZh: Any? = null,

  @Json(name = "open_status")
  val openStatus: Long,

  val introduction: String,

  @Json(name = "open_time")
  val openTime: String,

  val zipcode: String,
  val distric: String,
  val address: String,
  val tel: String,
  val fax: String,
  val email: String,
  val months: String,
  val nlat: Double,
  val elong: Double,

  @Json(name = "official_site")
  val officialSite: String,

  val facebook: String,
  val ticket: String,
  val remind: String,
  val staytime: String,
  val modified: String,
  val url: String,
  val category: List<Category>,
  val target: List<Category>,
  val service: List<Category>,
  val friendly: List<Any?>,
  val images: List<Image>,
  val files: List<Any?>,
  val links: List<Any?>
) {
//  public fun toJson() = moshi.toJsonString(this)
//
//  companion object {
//    public fun fromJson(json: String) = moshi.parse<Attractions>(json)
//  }
}

@JsonClass(generateAdapter = true)
data class Category (
  val id: Long,
  val name: String
)

@JsonClass(generateAdapter = true)
data class Image (
  val src: String,
  val subject: String,
  val ext: String
)
