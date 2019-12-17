package com.ivasenko.mobilelivetest.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.data.entity.TweetMapMarker
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_tweet.view.*

class SearchTweetsAdapter(var tweetList: List<TweetMapMarker> = emptyList()): RecyclerView.Adapter<TweetViewHolder>() {

    var handler: ((tweet: TweetMapMarker) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet, parent, false)
        return TweetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bindData(tweetList[position], handler)
    }
}

class TweetViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(tweet: TweetMapMarker, handler: ((tweet: TweetMapMarker) -> Unit)?) {
        containerView.tweet_text.text = tweet.getTitle()
        containerView.setOnClickListener { handler?.let { it(tweet) } }
    }
}