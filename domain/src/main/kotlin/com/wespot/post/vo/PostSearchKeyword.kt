package com.wespot.post.vo

import com.wespot.exception.CustomException
import org.springframework.http.HttpStatus

data class PostSearchKeyword(
    val keyword: String
) {

    companion object {

        private const val KEYWORD_LENGTH_LOWER_BOUND_EXCLUSIVE = 2
        private const val DELIMITER = " "
        private const val EDGE_OF_NUMBER_OF_WORDS_TO_SEARCH_EACH_WORD = 3

        fun from(keyword: String): PostSearchKeyword {
            return PostSearchKeyword(keyword.trim())
        }
    }

    init {
        if (keyword.length < KEYWORD_LENGTH_LOWER_BOUND_EXCLUSIVE) {
            throw CustomException(status = HttpStatus.BAD_REQUEST, message = "검색어는 최소 2글자 이상이어야 합니다.")
        }
    }

    fun keywordsToSearch(): List<String> {
        val keywordsForSearch = keyword.split(DELIMITER)

        if (keywordsForSearch.size <= EDGE_OF_NUMBER_OF_WORDS_TO_SEARCH_EACH_WORD) {
            return keywordsForSearch
        }

        return listOf(keyword)
    }

    fun keywordsToSearchInRegex(): String {
        val keywordsToSearch = keywordsToSearch()

        if (keywordsToSearch.size == 1) {
            return keywordsToSearch[0]
        }

        return keywordsToSearch.joinToString(separator = "|") { it.replace(" ", "") }
    }

}
