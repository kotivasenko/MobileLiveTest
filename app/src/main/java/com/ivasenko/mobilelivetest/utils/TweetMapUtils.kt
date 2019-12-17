package com.ivasenko.mobilelivetest.utils

import com.ivasenko.mobilelivetest.data.entity.Tweet
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker

internal fun getListResult(
    list: List<Tweet>,
    currentList: List<TweetMapMarker>?
): ListResult {
    val toRemoveList = mutableListOf<TweetMapMarker>()
    var newList = mutableListOf<TweetMapMarker>()
    currentList?.let { current ->
        newList = current.toMutableList()
    }
    list.forEach {
        val tweetMapMarker = TweetMapMarker(it)
        if (newList.size < 30) {
            newList.add(tweetMapMarker)
        }
        else {
            newList.firstOrNull { mapMarker -> mapMarker.created < tweetMapMarker.created }?.let { marker ->
                val index = newList.indexOf(marker)
                newList.remove(marker)
                newList.add(index, tweetMapMarker)
                toRemoveList.add(marker)
            }
        }
    }
    return ListResult(newList, toRemoveList)
}

data class ListResult(val currentList: List<TweetMapMarker>,
                      val listToRemove: List<TweetMapMarker>)
