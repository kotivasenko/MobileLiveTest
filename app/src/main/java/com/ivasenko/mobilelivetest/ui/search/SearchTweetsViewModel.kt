package com.ivasenko.mobilelivetest.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository
import kotlinx.coroutines.launch

class SearchTweetsViewModel(
    private val repository: TweeterRepository
) : ViewModel() {

    var searchText = ""
    private val _tweetList: MutableLiveData<List<TweetMapMarker>> = MutableLiveData()
    val tweetsList: LiveData<List<TweetMapMarker>>
        get() = _tweetList


    fun executeSearch() {
        viewModelScope.launch {
            val list = repository.fetchTweetsByText(searchText)
            _tweetList.postValue(list.map { TweetMapMarker(it) })
        }
    }
}
