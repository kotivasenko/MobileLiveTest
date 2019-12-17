package com.ivasenko.mobilelivetest.data.entity

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("coordinates") val coordinates: List<Double>,
    @SerializedName("type") val type: String
)