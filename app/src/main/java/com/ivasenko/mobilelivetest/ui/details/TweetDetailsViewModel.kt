package com.ivasenko.mobilelivetest.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository

class TweetDetailsViewModel(
    private val repository: TweeterRepository
) : ViewModel() {

    lateinit var tweet: TweetMapMarker

    private val _actionResult = MutableLiveData<Int>()
    val actionResult: LiveData<Int>
        get() = _actionResult

    fun addToFavorites() {
        repository.addToFavorite(tweet.tweets)
        _actionResult.postValue(R.string.tweet_favorited)
    }

    fun retweet() {
        repository.retweet(tweet.tweets)
        _actionResult.postValue(R.string.tweet_retweeted)
    }

}
