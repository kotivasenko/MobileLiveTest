package com.ivasenko.mobilelivetest.data.entity

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class TweetMapMarker(val tweets: Tweet) {

    var marker: Marker? = null
//    private val mtlLatLng = LatLng(45.4954954954955,-73.48938786482768)
    private val pattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    val created = getFormattedDate()
    val position = getRealOrFakePosition()

    fun getTitle(): String {
        return tweets.text
    }

    private fun getRealOrFakePosition(): LatLng {
        return tweets.coordinates?.let {
            LatLng(it.coordinates[1], it.coordinates[0])
        } ?: run {
            //fake location
            LatLng(Random.nextDouble(45.4954954954955, 45.8064954956955),
                Random.nextDouble(-73.80038786484768, -73.48938786482768))
        }
    }

    private fun getFormattedDate(): Long {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.isLenient = true
        return formatter.parse(tweets.createdAt).time
    }
}