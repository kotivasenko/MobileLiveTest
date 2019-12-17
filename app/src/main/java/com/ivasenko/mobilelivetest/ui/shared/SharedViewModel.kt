package com.ivasenko.mobilelivetest.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker

class SharedViewModel: ViewModel() {

    private val _tweetMapMarker = MutableLiveData<TweetMapMarker>()
    val tweetMapMarker: LiveData<TweetMapMarker>
        get() = _tweetMapMarker

    fun currentMarker(marker: TweetMapMarker) {
        _tweetMapMarker.value = marker
    }
}