package com.ivasenko.mobilelivetest.data.entity


import com.google.gson.annotations.SerializedName

data class Tweet(
    val contributors: String,
    val coordinates: Coordinates?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int,
    val favorited: Boolean,
    val geo: Geo,
    val id: Long,
    @SerializedName("id_str")
    val idStr: String,
    @SerializedName("in_reply_to_screen_name")
    val isQuoteStatus: Boolean,
    val lang: String,
    @SerializedName("possibly_sensitive")
    val possiblySensitive: Boolean,
    @SerializedName("retweet_count")
    val retweetCount: Int,
    val retweeted: Boolean,
    val source: String,
    val text: String,
    val truncated: Boolean,
    val user: User
)