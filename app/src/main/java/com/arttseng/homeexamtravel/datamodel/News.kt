package com.arttseng.homeexamtravel.datamodel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class News (
    val total: Long,
    val data: List<NewsData>
) {
//    public fun toJson() = klaxon.toJsonString(this)
//
//    companion object {
//        public fun fromJson(json: String) = klaxon.parse<Welcome8>(json)
//    }
}
@JsonClass(generateAdapter = true)
data class NewsData (
    val id: Long,
    val title: String,
    val description: String,
    val begin: Any? = null,
    val end: Any? = null,
    val posted: String,
    val modified: String,
    val url: String,
    val files: List<Any?>,
    val links: List<Link>
)
@JsonClass(generateAdapter = true)
data class Link (
    val src: String,
    val subject: String
)

