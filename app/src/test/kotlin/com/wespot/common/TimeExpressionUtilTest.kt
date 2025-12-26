package com.wespot.common

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDateTime

class TimeExpressionUtilTest {

    @Test
    fun `게시글의 시간을 표현합니다`() {
        // given
        val now = LocalDateTime.now()
        val fewSecondsAgo = now.minusSeconds(30)
        val severalMinutesAgo = now.minusMinutes(5)
        val severalHoursAgo = now.minusHours(3)
        val yesterday = now.minusDays(1)
        val severalMonthsAgo = now.minusMonths(4)
        val overAYearAgo = now.minusYears(2)

        // when
        val actual1 = TimeExpressionUtil.detailTime(now, fewSecondsAgo) // "방금"
        val actual2 = TimeExpressionUtil.detailTime(now, severalMinutesAgo) // "5분 전"
        val actual3 = TimeExpressionUtil.detailTime(now, severalHoursAgo) // "HH:MM"
        val actual4 = TimeExpressionUtil.detailTime(now, yesterday) // "MM/DD"
        val actual5 = TimeExpressionUtil.detailTime(now, severalMonthsAgo) // "MM/DD"
        val actual6 = TimeExpressionUtil.detailTime(now, overAYearAgo) // "2년 전"

        // then
        assertEquals("방금", actual1)
        assertEquals("5분 전", actual2)
        assertEquals(
            "${severalHoursAgo.hour.toString().padStart(2, '0')}:${
                severalHoursAgo.minute.toString().padStart(2, '0')
            }", actual3
        )
        assertEquals(
            "${yesterday.monthValue.toString().padStart(2, '0')}/${
                yesterday.dayOfMonth.toString().padStart(2, '0')
            }", actual4
        )
        assertEquals(
            "${
                severalMonthsAgo.monthValue.toString().padStart(2, '0')
            }/${severalMonthsAgo.dayOfMonth.toString().padStart(2, '0')}", actual5
        )
        assertEquals("2년 전", actual6)
    }

    @Test
    fun `댓글의 시간을 표현합니다`() {
        // given
        val now = LocalDateTime.now()
        val fewSecondsAgo = now.minusSeconds(30)
        val severalMinutesAgo = now.minusMinutes(5)
        val severalHoursAgo = now.minusHours(3)
        val yesterday = now.minusYears(1)

        // when
        val actual1 = TimeExpressionUtil.isNotDetailTime(now, fewSecondsAgo) // "방금"
        val actual2 = TimeExpressionUtil.isNotDetailTime(now, severalMinutesAgo) // "MM/DD HH:MM"
        val actual3 = TimeExpressionUtil.isNotDetailTime(now, severalHoursAgo) // "MM/DD HH:MM"
        val actual4 = TimeExpressionUtil.isNotDetailTime(now, yesterday) // "YY/MM/DD HH:MM"

        // then
        assertEquals("방금", actual1)
        assertEquals(
            "${severalMinutesAgo.monthValue.toString().padStart(2, '0')}/${
                severalMinutesAgo.dayOfMonth.toString().padStart(2, '0')
            } ${
                severalMinutesAgo.hour.toString().padStart(2, '0')
            }:${severalMinutesAgo.minute.toString().padStart(2, '0')}", actual2
        )
        assertEquals(
            "${severalHoursAgo.monthValue.toString().padStart(2, '0')}/${
                severalHoursAgo.dayOfMonth.toString().padStart(2, '0')
            } ${
                severalHoursAgo.hour.toString().padStart(2, '0')
            }:${severalHoursAgo.minute.toString().padStart(2, '0')}", actual3
        )
        assertEquals(
            "${(yesterday.year % 100).toString().padStart(2, '0')}/${
                yesterday.monthValue.toString().padStart(2, '0')
            }/${yesterday.dayOfMonth.toString().padStart(2, '0')} ${
                yesterday.hour.toString().padStart(2, '0')
            }:${yesterday.minute.toString().padStart(2, '0')}", actual4
        )
    }

}
