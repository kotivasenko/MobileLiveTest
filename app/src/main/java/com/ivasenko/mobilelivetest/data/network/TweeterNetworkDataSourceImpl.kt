package com.ivasenko.mobilelivetest.data.network

import android.util.Base64
import com.ivasenko.mobilelivetest.data.entity.Token
import com.ivasenko.mobilelivetest.data.entity.Tweet
import com.ivasenko.mobilelivetest.data.entity.UserLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class TweeterNetworkDataSourceImpl(
    private val tweeterApiService: TweeterApiService
) : TweeterNetworkDataSource {

    private val key = "zzGINEmSJfS5dRubkXltES2ys"
    private val secret = "WMkv0mr67XzzdYRIksXONQScbzqidqjcuFhj2YT8C7lMJZUDdF"
    private val accessToken = "2817116276-y0EUOrUCnpdhpWoZocqvRaQLqZSejbNsLy2mvV0"
    private val accessTokenSecret = "aNMGnw0E7WKtS5CDQ3EDI44LUN0yPf7Gm5UQ5h7Gjz4eG"
    private lateinit var token: Token

    private val lang = Locale.getDefault().language

    override suspend fun fetchToken(): Token {
        return tweeterApiService.fetchToken(getAuthorizationCredentials(), "client_credentials")
            .also {
                token = it
            }
    }

    override suspend fun fetchTweets(location: UserLocation, token: Token): List<Tweet> {
        return tweeterApiService.fetchTweets(
            credentials = "Bearer ${token.accessToken}",
            geocode = "${location.latitude},${location.longitude},${location.radius}km",
            count = 10,
            lang = lang
        ).statuses
    }

    override suspend fun fetchTweetsByText(searchText: String): List<Tweet> {
        return tweeterApiService.fetchTweetsByText(
            credentials = "Bearer ${token.accessToken}",
            query = searchText,
            count = 100,
            lang = lang
        ).statuses
    }

    override fun addToFavorite(tweets: Tweet) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tweeterApiService.postFavorite(
                    generateOAuthAuthorizationHeader("https://api.twitter.com/1.1/favorites/create.json"),
                    tweets.id
                )
            } catch (ex: Exception) {
            }
        }
    }

    override fun retweet(tweets: Tweet) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tweeterApiService.postRetweet(
                    generateOAuthAuthorizationHeader("https://api.twitter.com/1.1/statuses/retweet/:id.json"),
                    tweets.id
                )
            } catch (ex: Exception) {
            }
        }
    }

    private fun getAuthorizationCredentials(): String {
        val keyEncoded = URLEncoder.encode(key, "UTF-8")
        val secretEncoded = URLEncoder.encode(secret, "UTF-8")
        val combined = "$keyEncoded:$secretEncoded"
        val base64 = Base64.encodeToString(combined.toByteArray(), Base64.NO_WRAP)
        return "Basic $base64"
    }

    private fun generateOAuthAuthorizationHeader(url: String): String {
        val timeStamp = System.currentTimeMillis() / 1000
        val nonce = UUID.randomUUID().toString().replace("-", "")
        val params = HashMap<String, String>()
        params["oauth_consumer_key"] = key
        params["oauth_nonce"] = nonce
        params["oauth_signature_method"] = "HMAC-SHA1"
        params["oauth_timestamp"] = timeStamp.toString()
        params["oauth_token"] = accessToken
        params["oauth_version"] = "1.0"

        return generateOAuthHeader(params, url)
    }

    private fun generateOAuthHeader(params: HashMap<String, String>, url: String): String {
        val encodedParams = HashMap<String, String>()
        params.forEach { (key, value) ->
            encodedParams[percentEncode(key)] = percentEncode(value)
        }
        encodedParams.toSortedMap()

        val encodedList = mutableListOf<String>()
        encodedParams.forEach { (key, value) -> encodedList.add("$key=$value") }
        val paramsString = encodedList.joinToString("&")
        val baseString = "POST&${percentEncode(url)}&$paramsString"
        val signingKey = percentEncode(secret) + "&" + percentEncode(accessTokenSecret)

        params["oauth_signature"] = calculateRFC2104HMAC(baseString, signingKey)
        params.toSortedMap()
        val encodedHeaderValues = mutableListOf<String>()
        params.forEach { (key, value) ->
            encodedHeaderValues.add(
                percentEncode(key) + "=" + "\"" + percentEncode(value) + "\""
            )
        }

        return "OAuth ${encodedHeaderValues.joinToString(", ")}"
    }

    private fun percentEncode(s: String?): String {
        s?.let {
            try {
                return URLEncoder.encode(s, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~")
            } catch (wow: UnsupportedEncodingException) {
                throw RuntimeException(wow.message, wow)
            }
        }
        return ""
    }

    private fun calculateRFC2104HMAC(data: String, key: String): String {
        val signingKey = SecretKeySpec(key.toByteArray(), "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(signingKey)
        return Base64.encodeToString(mac.doFinal(data.toByteArray()), Base64.DEFAULT).trim()
    }
}