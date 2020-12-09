package com.aptiveresources.sptwitterapi.externalAPI

import com.aptiveresources.sptwitterapi.models.BearerToken
import com.aptiveresources.sptwitterapi.models.SearchValues
import com.aptiveresources.sptwitterapi.models.Tweet
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.logging.Logger

class TweetsView(
    private val searchValues: SearchValues,
    private val bearerToken: BearerToken
) {
    private val twitterAPI: TwitterAPI = WebClientModule.twitterAPI
    val tweets = ConcurrentLinkedQueue<Tweet>()

    suspend fun getSearchTweetsAndSave() {
        coroutineScope {
            searchValues.searches.map {
                async(Dispatchers.IO) {
                    getSearchTweets(it)
                }
            }.awaitAll()
        }
    }

    private fun getSearchTweets(query: String, maxId: Long? = null) {
        val getSearchTweetsResponse = twitterAPI.getSearchTweets(
            authorization = "Bearer ${bearerToken.accessToken}",
            query = query,
            maxId = maxId
        ).execute()

        return if (getSearchTweetsResponse.isSuccessful && !getSearchTweetsResponse.body()?.statuses.isNullOrEmpty()) {
            val searchTweetsResponse = getSearchTweetsResponse.body()!!
            tweets.addAll(searchTweetsResponse.statuses)

            val newMaxId: Long = searchTweetsResponse.statuses.map { it.id }.minOrNull()!!.minus(1)
            getSearchTweets(query, newMaxId)
        } else {
            log.info("finished pulling tweets for query: { $query }")
        }
    }

    suspend fun getUserTimelineTweetsAndSave() {
        coroutineScope {
            searchValues.fromAccounts!!.map {
                async(Dispatchers.IO) {
                    getUserTimelineTweets(it)
                }
            }.awaitAll()
        }
    }

    private fun getUserTimelineTweets(user: String, maxId: Long? = null) {
        val getUserTimelineResponse = twitterAPI.getUserTimeline(
            authorization = "Bearer ${bearerToken.accessToken}",
            screenName = user,
            maxId = maxId
        ).execute()

        return if (getUserTimelineResponse.isSuccessful && !getUserTimelineResponse.body().isNullOrEmpty()) {
            val userTimelineResponse = getUserTimelineResponse.body()!!
            tweets.addAll(userTimelineResponse)

            val newMaxId: Long = userTimelineResponse.map { it.id }.minOrNull()!!.minus(1)
            getUserTimelineTweets(user, newMaxId)
        } else {
            log.info("finished pulling tweets for user: $user")
        }
    }

    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }
}
