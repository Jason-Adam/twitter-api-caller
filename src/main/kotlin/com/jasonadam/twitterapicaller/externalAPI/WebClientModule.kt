package com.jasonadam.twitterapicaller.externalAPI

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration

object WebClientModule {

    val twitterAPI: TwitterAPI by lazy {
        val jacksonConverterFactory = JacksonConverterFactory.create(objectMapper)
        Retrofit.Builder()
            .baseUrl("https://api.twitter.com")
            .addConverterFactory(jacksonConverterFactory)
            .client(httpClient)
            .build()
            .create(TwitterAPI::class.java)
    }

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val httpClient: OkHttpClient
        get() = OkHttpClient()
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(60L))
            .readTimeout(Duration.ofSeconds(60L))
            .build()
}