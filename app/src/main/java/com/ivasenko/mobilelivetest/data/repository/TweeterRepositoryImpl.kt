package com.ivasenko.mobilelivetest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ivasenko.mobilelivetest.data.entity.Tweet
import com.ivasenko.mobilelivetest.data.entity.UserLocation
import com.ivasenko.mobilelivetest.data.network.TweeterNetworkDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

class TweeterRepositoryImpl(
    private val tweeterNetworkDataSource: TweeterNetworkDataSource
) : TweeterRepository {

    private val _tweetList: MutableLiveData<List<Tweet>> = MutableLiveData()
    override val tweetsList: LiveData<List<Tweet>>
        get() = _tweetList


    override suspend fun fetchTweets(location: UserLocation) {
        val token = tweeterNetworkDataSource.fetchToken()
        while (coroutineContext.isActive) {
            val tweetList = tweeterNetworkDataSource.fetchTweets(location, token)
            _tweetList.postValue(tweetList)
            delay(30_000)
        }
    }

    override suspend fun fetchTweetsByText(searchText: String): List<Tweet> {
        return tweeterNetworkDataSource.fetchTweetsByText(searchText)
    }

    override fun addToFavorite(tweets: Tweet) {
        tweeterNetworkDataSource.addToFavorite(tweets)
    }

    override fun retweet(tweets: Tweet) {
        tweeterNetworkDataSource.retweet(tweets)
    }
}