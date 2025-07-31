package com.wespot.post.vo

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class PostSearchKeywordTest {

    @Test
    fun `단어가 3개 이하이면 | 로 이어진 키워드가 나옵니다`() {
        // given
        val keyword = "안녕 만나서 반가워"
        val postSearchKeyword = PostSearchKeyword.from(keyword)

        // when
        val actual = postSearchKeyword.keywordsToSearchInRegex()

        // then
        assertEquals("안녕|만나서|반가워", actual)
    }

    @Test
    fun `단어가 3개 이상이면 하나의 단어로 나옵니다`() {
        // given
        val keyword = "안녕 만나서 반가워 오늘은 날씨가 좋네요"
        val postSearchKeyword = PostSearchKeyword.from(keyword)

        // when
        val actual = postSearchKeyword.keywordsToSearchInRegex()

        // then
        assertEquals("안녕 만나서 반가워 오늘은 날씨가 좋네요", actual)
    }


}
