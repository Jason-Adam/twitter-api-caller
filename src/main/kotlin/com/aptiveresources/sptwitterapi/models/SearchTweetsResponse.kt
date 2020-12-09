package com.aptiveresources.sptwitterapi.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SearchTweetsResponse(
    @JsonProperty("statuses") val statuses: List<Tweet>
)

data class Tweet(
    @JsonProperty("created_at") val createdAt: String,
    @JsonProperty("id") val id: Long,
    @JsonProperty("text") val text: String,
    @JsonProperty("truncated") val truncated: Boolean,
    @JsonProperty("source") val source: String?,
    @JsonProperty("in_reply_to_status_id") val inReplyToStatusId: Long?,
    @JsonProperty("in_reply_to_user_id") val inReplyToUserId: Long?,
    @JsonProperty("user") val user: User,
    @JsonProperty("place") val place: Place?,
    @JsonProperty("quoted_status_id") val quotedStatusId: Long?,
    @JsonProperty("is_quote_status") val isQuoteStatus: Boolean,
    @JsonProperty("quoted_status") val quotedStatus: Tweet?,
    @JsonProperty("retweeted_status") val retweetedStatus: Tweet?,
    @JsonProperty("quote_count") val quoteCount: Int?, // Enterprise Only
    @JsonProperty("reply_count") val replyCount: Int?, // Enterprise Only
    @JsonProperty("retweet_count") val retweetCount: Int,
    @JsonProperty("favorite_count") val favoriteCount: Int?,
    @JsonProperty("entities") val entities: Entities
) {

    data class User(
        @JsonProperty("id") val id: Long,
        @JsonProperty("name") val user: String,
        @JsonProperty("screen_name") val screenName: String,
        @JsonProperty("location") val location: String?,
        @JsonProperty("url") val url: String?,
        @JsonProperty("description") val description: String?,
        @JsonProperty("verified") val verified: Boolean,
        @JsonProperty("followers_count") val followersCount: Int,
        @JsonProperty("friends_count") val friendsCount: Int,
        @JsonProperty("listed_count") val listedCount: Int,
        @JsonProperty("favourites_count") val favouritesCount: Int,
        @JsonProperty("statuses_count") val statusesCount: Int,
        @JsonProperty("created_at") val createdAt: String
    )

    data class Place(
        @JsonProperty("id") val id: String,
        @JsonProperty("place_type") val placeType: String,
        @JsonProperty("name") val name: String,
        @JsonProperty("full_name") val fullName: String,
        @JsonProperty("country_code") val countryCode: String,
        @JsonProperty("country") val country: String
    )

    data class Entities(
        @JsonProperty("hashtags") val hashtags: List<Hashtag>,
        @JsonProperty("user_mentions") val userMentions: List<UserMention>
    )

    data class Hashtag(
        @JsonProperty("text") val text: String
    )

    data class UserMention(
        @JsonProperty("id") val id: Long,
        @JsonProperty("name") val name: String,
        @JsonProperty("screen_name") val screenName: String
    )
}
