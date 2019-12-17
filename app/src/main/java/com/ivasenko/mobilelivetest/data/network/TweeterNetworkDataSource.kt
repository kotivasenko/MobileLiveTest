package com.ivasenko.mobilelivetest.data.network

import com.ivasenko.mobilelivetest.data.entity.Token
import com.ivasenko.mobilelivetest.data.entity.Tweet
import com.ivasenko.mobilelivetest.data.entity.UserLocation

interface TweeterNetworkDataSource {
    suspend fun fetchToken(): Token
    suspend fun fetchTweets(location: UserLocation, token: Token): List<Tweet>
    suspend fun fetchTweetsByText(searchText: String): List<Tweet>
    fun addToFavorite(tweets: Tweet)
    fun retweet(tweets: Tweet)
}