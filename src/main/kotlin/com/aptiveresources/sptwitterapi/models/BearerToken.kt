package com.aptiveresources.sptwitterapi.models

import com.fasterxml.jackson.annotation.JsonProperty

data class BearerToken(
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("access_token") val accessToken: String
)
