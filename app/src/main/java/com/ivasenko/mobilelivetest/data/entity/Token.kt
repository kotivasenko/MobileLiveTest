package com.ivasenko.mobilelivetest.data.entity

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("access_token") val accessToken: String
)