package com.ivasenko.mobilelivetest.data.entity


import com.google.gson.annotations.SerializedName

data class User(
    val `protected`: Boolean,
    @SerializedName("contributors_enabled")
    val contributorsEnabled: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_profile")
    val defaultProfile: Boolean,
    @SerializedName("default_profile_image")
    val defaultProfileImage: Boolean,
    val description: String,
    @SerializedName("favourites_count")
    val favouritesCount: Long,
    @SerializedName("follow_request_sent")
    val followRequestSent: Boolean,
    @SerializedName("followers_count")
    val followersCount: Long,
    val following: Boolean,
    @SerializedName("friends_count")
    val friendsCount: Long,
    @SerializedName("geo_enabled")
    val geoEnabled: Boolean,
    val id: Long,
    @SerializedName("id_str")
    val idStr: String,
    @SerializedName("is_translator")
    val isTranslator: Boolean,
    val lang: String,
    @SerializedName("listed_count")
    val listedCount: Long,
    val location: String,
    val name: String,
    val notifications: Boolean,
    @SerializedName("profile_background_color")
    val profileBackgroundColor: String,
    @SerializedName("profile_background_image_url")
    val profileBackgroundImageUrl: String,
    @SerializedName("profile_background_image_url_https")
    val profileBackgroundImageUrlHttps: String,
    @SerializedName("profile_banner_url")
    val profileBannerUrl: String,
    @SerializedName("profile_image_url")
    val profileImageUrl: String,
    @SerializedName("profile_image_url_https")
    val profileImageUrlHttps: String,
    @SerializedName("profile_link_color")
    val profileLinkColor: String,
    @SerializedName("profile_sidebar_border_color")
    val profileSidebarBorderColor: String,
    @SerializedName("profile_sidebar_fill_color")
    val profileSidebarFillColor: String,
    @SerializedName("profile_text_color")
    val profileTextColor: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName("statuses_count")
    val statusesCount: Long,
    @SerializedName("translator_type")
    val translatorType: String,
    val url: String,
    val verified: Boolean
)