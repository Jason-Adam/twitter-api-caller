package com.aptiveresources.sptwitterapi.externalAPI

import com.aptiveresources.sptwitterapi.models.BearerToken
import java.util.logging.Logger

class BearerTokenView {
    private val twitterAPI: TwitterAPI = WebClientModule.twitterAPI

    fun getBearerToken(): BearerToken {
        log.info("retrieving bearer token for subsequent calls")
        val postBearerTokenResponse = twitterAPI.postOauth2Token().execute()

        return if (postBearerTokenResponse.isSuccessful) {
            log.info("successfully retrieved bearer token")
            postBearerTokenResponse.body()!!
        } else {
            val errorMessage =
                "failed to retrieve bearer token with error: ${postBearerTokenResponse.errorBody()?.string()}"
            log.severe(errorMessage)
            throw Exception(errorMessage)
        }
    }

    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }
}