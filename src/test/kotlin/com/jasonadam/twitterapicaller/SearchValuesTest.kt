package com.jasonadam.twitterapicaller

import com.jasonadam.twitterapicaller.models.SearchValues
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate

class SearchValuesTest {


    @ParameterizedTest
    @MethodSource("searches")
    fun testSearchValuesSearchTermsQueryOperator(input: List<String>, expected: List<String>) {
        val searchValue = SearchValues(searchTerms = input, sinceDate = sinceDate)
        val actual = searchValue.searches
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("accountMentions")
    fun testSearchValuesAccountMentionsQueryOperator(input: List<String>, expected: List<String>) {
        val searchValue = SearchValues(accountMentions = input, sinceDate = sinceDate)
        val actual = searchValue.searches
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("repliedToAccounts")
    fun testSearchValuesRepliedToAccountsQueryOperator(input: List<String>, expected: List<String>) {
        val searchValue = SearchValues(repliedToAccounts = input, sinceDate = sinceDate)
        val actual = searchValue.searches
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("searchCompoundPhrases")
    fun testSearchValuesSearchCompoundPhrasesQueryOperator(input: List<String>, expected: List<String>) {
        val searchValue = SearchValues(searchCompoundPhrases = input, sinceDate = sinceDate)
        val actual = searchValue.searches
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("allSearches")
    fun testSearchValuesAllSearches(
        searchTerms: List<String>,
        accountMentions: List<String>,
        repliedToAccounts: List<String>,
        searchCompoundPhrases: List<String>,
        expected: List<String>
    ) {
        val searchValue = SearchValues(
            searchTerms = searchTerms,
            accountMentions = accountMentions,
            repliedToAccounts = repliedToAccounts,
            searchCompoundPhrases = searchCompoundPhrases,
            sinceDate = sinceDate
        )
        val actual = searchValue.searches
        Assertions.assertEquals(expected, actual)
    }

    companion object {
        private val sinceDate = LocalDate.now().minusDays(2).toString()

        @JvmStatic
        fun searches() = listOf(
            Arguments.of(
                listOf("veteransaffairs", "vahospital"),
                listOf("veteransaffairs OR vahospital since:$sinceDate")
            ),
            Arguments.of(
                listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"),
                listOf(
                    "a OR b OR c OR d OR e OR f OR g OR h OR i OR j since:$sinceDate",
                    "k since:$sinceDate"
                )
            )
        )

        @JvmStatic
        fun accountMentions() = listOf(
            Arguments.of(
                listOf("@VetAffairsOIG", "@VAVetBenefits"),
                listOf("@VetAffairsOIG OR @VAVetBenefits since:$sinceDate")
            ),
            Arguments.of(
                listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"),
                listOf(
                    "a OR b OR c OR d OR e OR f OR g OR h OR i OR j since:$sinceDate",
                    "k since:$sinceDate"
                )
            )
        )

        @JvmStatic
        fun repliedToAccounts() = listOf(
            Arguments.of(
                listOf("VetAffairsOIG", "VAVetBenefits"),
                listOf("to:VetAffairsOIG OR to:VAVetBenefits since:$sinceDate")
            ),
            Arguments.of(
                listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"),
                listOf(
                    "to:a OR to:b OR to:c OR to:d OR to:e OR to:f OR to:g OR to:h OR to:i OR to:j since:$sinceDate",
                    "to:k since:$sinceDate"
                )
            )
        )

        @JvmStatic
        fun searchCompoundPhrases() = listOf(
            Arguments.of(
                listOf("veteran health", "veteran issues"),
                listOf(
                    """
                    "veteran health" OR "veteran issues" since:$sinceDate
                """.trimIndent()
                )
            ),
            Arguments.of(
                listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"),
                listOf(
                    """
                        "a" OR "b" OR "c" OR "d" OR "e" OR "f" OR "g" OR "h" OR "i" OR "j" since:$sinceDate
                    """.trimIndent(),
                    """
                        "k" since:$sinceDate
                    """.trimIndent()
                )
            )
        )

        @JvmStatic
        fun allSearches() = listOf(
            Arguments.of(
                listOf("veteransaffairs", "vahospital"),
                listOf("@VetAffairsOIG", "@VAVetBenefits"),
                listOf("VetAffairsOIG", "VAVetBenefits"),
                listOf("veteran health", "veteran issues"),
                listOf(
                    "veteransaffairs OR vahospital since:$sinceDate",
                    "@VetAffairsOIG OR @VAVetBenefits since:$sinceDate",
                    "to:VetAffairsOIG OR to:VAVetBenefits since:$sinceDate",
                    """
                        "veteran health" OR "veteran issues" since:$sinceDate
                    """.trimIndent()
                )
            )
        )
    }
}