package com.ivasenko.mobilelivetest.data.network

import com.ivasenko.mobilelivetest.data.entity.Token
import com.ivasenko.mobilelivetest.data.entity.Tweets
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TweeterApiService {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
    @POST("oauth2/token")
    suspend fun fetchToken(
        @Header("Authorization") credentials: String,
        @Field("grant_type") type: String
    ): Token

    @Headers("Content-Type: application/json")
    @GET("1.1/search/tweets.json")
    suspend fun fetchTweets(
        @Header("Authorization") credentials: String,
        @Query("q") query: String = "",
        @Query("geocode") geocode: String,
        @Query("count") count: Int,
        @Query("result_type") type: String = "recent",
        @Query("lang") lang: String
    ): Tweets

    @Headers("Content-Type: application/json")
    @GET("1.1/search/tweets.json")
    suspend fun fetchTweetsByText(
        @Header("Authorization") credentials: String,
        @Query("q") query: String = "",
        @Query("count") count: Int,
        @Query("result_type") type: String = "recent",
        @Query("lang") lang: String
    ): Tweets

    @Headers("Content-Type: application/json")
    @POST("1.1/favorites/create.json")
    suspend fun postFavorite(
        @Header("Authorization") credentials: String,
        @Query("id") tweetId: Long
    )

    @Headers("Content-Type: application/json")
    @POST("1.1/statuses/retweet/:id.json")
    fun postRetweet(
        @Header("authorization") credentials: String,
        @Query("id") tweetId: Long
    )

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TweeterApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.twitter.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TweeterApiService::class.java)
        }
    }
}