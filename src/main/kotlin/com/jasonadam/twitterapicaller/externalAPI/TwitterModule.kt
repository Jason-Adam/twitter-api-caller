package com.jasonadam.twitterapicaller.externalAPI

import java.util.*

object TwitterModule {

    private val clientKey: String = System.getenv("TWITTER_CLIENT_KEY")
    private val clientSecret: String = System.getenv("TWITTER_CLIENT_SECRET")

    val encodedKey: String
        get() {
            val combinedKey: ByteArray = "$clientKey:$clientSecret".toByteArray()
            val encodedKey: String =  Base64.getEncoder().encodeToString(combinedKey)
            return "Basic $encodedKey"
        }

    const val apiVersion = "1.1"
}
