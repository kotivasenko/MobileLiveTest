package com.ivasenko.mobilelivetest.ui.map

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.databinding.LayoutTweetViewBinding

class TweetView(private val inflater: LayoutInflater): GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View {
        val binding: LayoutTweetViewBinding = DataBindingUtil.inflate(inflater, R.layout.layout_tweet_view, null, false)
        binding.tweetTextView.text = marker.title
        return binding.root
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}