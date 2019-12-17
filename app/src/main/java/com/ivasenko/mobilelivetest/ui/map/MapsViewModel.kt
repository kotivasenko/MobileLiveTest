package com.ivasenko.mobilelivetest.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker
import com.ivasenko.mobilelivetest.data.entity.UserLocation
import com.ivasenko.mobilelivetest.data.repository.TweeterRepository
import com.ivasenko.mobilelivetest.ui.dialogs.RadiusDialogListener
import com.ivasenko.mobilelivetest.utils.ListResult
import com.ivasenko.mobilelivetest.utils.getListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel(
    private val repository: TweeterRepository
) : ViewModel(), RadiusDialogListener {

    var radius = 5
    private lateinit var defaultLocation: LatLng
    private val listResult: LiveData<ListResult> = Transformations.map(repository.tweetsList) {
        getListResult(it, tweetsList.value)
    }
    private val _tweetsList: LiveData<List<TweetMapMarker>> = Transformations.map(listResult) { result ->
        viewModelScope.launch(Dispatchers.Main) {
            result.listToRemove.forEach { it.marker?.remove() }
        }
        result.currentList
    }
    val tweetsList: LiveData<List<TweetMapMarker>>
        get() = _tweetsList

    fun loadTweets(position: LatLng) {
        defaultLocation = position
        val location = UserLocation(position.latitude, position.longitude, radius)
        viewModelScope.launch {
            fetchTweets(location)
        }
    }

    private suspend fun fetchTweets(location: UserLocation) {
        repository.fetchTweets(location)
    }

    fun getTweetMapMarker(marker: Marker): TweetMapMarker? {
        return tweetsList.value?.let { list ->
            list.find { it.marker == marker }
        }
    }

    override fun onNewRadius(newRadius: Int) {
        radius = newRadius
        val location = UserLocation(defaultLocation.latitude,
            defaultLocation.longitude,
            newRadius)
        viewModelScope.launch {
            fetchTweets(location)
        }
    }
}
