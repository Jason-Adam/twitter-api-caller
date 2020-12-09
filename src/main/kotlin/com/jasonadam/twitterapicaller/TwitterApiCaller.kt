package com.jasonadam.twitterapicaller

import com.jasonadam.twitterapicaller.externalAPI.BearerTokenView
import com.jasonadam.twitterapicaller.externalAPI.TweetsView
import com.jasonadam.twitterapicaller.models.SearchValues
import com.jasonadam.twitterapicaller.modules.GcsStorageWriter
import com.jasonadam.twitterapicaller.modules.ResourceLoader
import com.jasonadam.twitterapicaller.modules.StorageWriter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    // Load Local Config
    val objectMapper = jacksonObjectMapper()
    val testSearchValues: SearchValues = objectMapper
        .readValue(ResourceLoader.loadFileAsResource()!!)

    // Retrieve Bearer Token for Subsequent API Calls
    val bearerToken = BearerTokenView().getBearerToken()
    val tweetsView = TweetsView(searchValues = testSearchValues, bearerToken = bearerToken)

    // Query Twitter
    runBlocking {
        withContext(Dispatchers.Default) { tweetsView.getSearchTweetsAndSave() }
        withContext(Dispatchers.Default) { tweetsView.getUserTimelineTweetsAndSave() }
    }

    // Load JSON to GCS
    val storageWriter: StorageWriter = GcsStorageWriter()
    storageWriter.loadToStorage(objectMapper.writeValueAsString(tweetsView.tweets))
}