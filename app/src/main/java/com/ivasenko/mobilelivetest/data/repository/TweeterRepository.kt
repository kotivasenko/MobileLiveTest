package com.ivasenko.mobilelivetest.data.repository

import androidx.lifecycle.LiveData
import com.ivasenko.mobilelivetest.data.entity.Tweet
import com.ivasenko.mobilelivetest.data.entity.UserLocation

interface TweeterRepository {
    val tweetsList: LiveData<List<Tweet>>
    suspend fun fetchTweets(location: UserLocation)
    suspend fun fetchTweetsByText(searchText: String): List<Tweet>
    fun addToFavorite(tweets: Tweet)
    fun retweet(tweets: Tweet)
}