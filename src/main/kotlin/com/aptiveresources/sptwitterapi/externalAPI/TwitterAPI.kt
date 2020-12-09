package com.aptiveresources.sptwitterapi.externalAPI

import com.aptiveresources.sptwitterapi.models.BearerToken
import com.aptiveresources.sptwitterapi.models.SearchTweetsResponse
import com.aptiveresources.sptwitterapi.models.Tweet
import retrofit2.Call
import retrofit2.http.*

interface TwitterAPI {

    @POST("/oauth2/token")
    fun postOauth2Token(
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded;charset=UTF-8",
        @Header("Authorization") authorization: String = TwitterModule.encodedKey,
        @Query("grant_type") grantType: String = "client_credentials"
    ): Call<BearerToken>

    @GET("/{apiVersion}/search/tweets.json")
    fun getSearchTweets(
        @Header("Authorization") authorization: String,
        @Path("apiVersion") apiVersion: String? = TwitterModule.apiVersion,
        @Query("q") query: String,
        @Query("max_id") maxId: Long?,
        @Query("lang") language: String? = "en",
        @Query("result_type") resultType: String? = "recent",
        @Query("include_entities") includeEntities: Boolean? = true,
        @Query("count") count: Int? = 100
    ): Call<SearchTweetsResponse>

    @GET("/{apiVersion}/statuses/user_timeline.json")
    fun getUserTimeline(
        @Header("Authorization") authorization: String,
        @Path("apiVersion") apiVersion: String? = TwitterModule.apiVersion,
        @Query("user_id") userId: Int? = null,
        @Query("screen_name") screenName: String?,
        @Query("since_id") sinceId: Long? = null,
        @Query("max_id") maxId: Long? = null,
        @Query("count") count: Int? = 200,
        @Query("include_rts") includeRts: Boolean? = true
    ): Call<List<Tweet>>
}
