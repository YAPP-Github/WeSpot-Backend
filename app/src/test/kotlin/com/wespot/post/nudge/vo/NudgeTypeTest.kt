package com.wespot.post.nudge.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NudgeTypeTest {

    @Test
    fun `Nudging Type을 순서에 따라 반환받습니다1`() {
        // given
        val startSequence = 1
        val endSequence = 15

        // when
        val actual = NudgeType.nudgeTypesByInquirySequences(startSequence, endSequence)

        // then
        assertThat(actual).isEqualTo(
            listOf(
                NudgeType.VOTE,
                NudgeType.HOT_POST,
            )
        )
    }

    @Test
    fun `Nudging Type을 순서에 따라 반환받습니다2`() {
        // given
        val startSequence = 1
        val endSequence = 100

        // when
        val actual = NudgeType.nudgeTypesByInquirySequences(startSequence, endSequence)

        // then
        assertThat(actual).isEqualTo(
            listOf(
                NudgeType.VOTE,
                NudgeType.HOT_POST,
                NudgeType.MESSAGE,
                NudgeType.VOTE,
            )
        )
    }

    @Test
    fun `Nudging Type을 순서에 따라 반환받습니다3`() {
        // given
        val startSequence = 46
        val endSequence = 100

        // when
        val actual = NudgeType.nudgeTypesByInquirySequences(startSequence, endSequence)

        // then
        assertThat(actual).isEqualTo(
            listOf(
                NudgeType.VOTE,
            )
        )
    }

    @Test
    fun `Nudging Type을 순서에 따라 반환받습니다4`() {
        // given
        val startSequence = 90
        val endSequence = 135

        // when
        val actual = NudgeType.nudgeTypesByInquirySequences(startSequence, endSequence)

        // then
        assertThat(actual).isEqualTo(
            listOf(
                NudgeType.VOTE,
                NudgeType.MESSAGE,
            )
        )
    }

}
