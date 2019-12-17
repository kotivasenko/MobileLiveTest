package com.ivasenko.mobilelivetest.data.entity

import com.google.gson.annotations.SerializedName

data class Geo(

    @SerializedName("coordinates") val coordinates: List<Double>,
    @SerializedName("type") val type: String
)