package com.jasonadam.twitterapicaller.models

import java.time.LocalDate

data class SearchValues(
    val fromAccounts: List<String>? = null,
    val searchTerms: List<String>? = null,
    val accountMentions: List<String>? = null,
    val repliedToAccounts: List<String>? = null,
    val searchCompoundPhrases: List<String>? = null,
    private val sinceDate: String = LocalDate.now().minusDays(2).toString()
) {
    private val searchTermsQueryOperator: List<String>
        get() = searchTerms?.chunked(10)
            ?.map { it.joinToString(separator = " OR ", postfix = " since:$sinceDate") }
            ?: emptyList()

    private val accountMentionsOperator: List<String>
        get() = accountMentions?.chunked(10)
            ?.map { it.joinToString(separator = " OR ", postfix = " since:$sinceDate") }
            ?: emptyList()

    private val repliedToAccountsOperator: List<String>
        get() = repliedToAccounts?.chunked(10)
            ?.map { it.joinToString(separator = " OR to:", prefix = "to:", postfix = " since:$sinceDate") }
            ?: emptyList()

    private val searchCompoundPhrasesOperator: List<String>
        get() = searchCompoundPhrases?.chunked(10)
            ?.map {
                it.joinToString(
                    separator = """" OR """",
                    prefix = """"""",
                    postfix = """" since:$sinceDate"""
                )
            }
            ?: emptyList()

    val searches: List<String>
        get() {
            val output = listOf(
                searchTermsQueryOperator,
                accountMentionsOperator,
                repliedToAccountsOperator,
                searchCompoundPhrasesOperator
            ).flatten()
            return if (output.isNotEmpty()) output else throw Exception("no searches were provided")
        }
}