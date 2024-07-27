package com.wespot.message.service

import java.time.Clock
import java.time.LocalTime

object MessageTimeValidator {
    private var clock: Clock = Clock.systemDefaultZone()

    fun setClock(clock: Clock) {
        this.clock = clock
    }

    fun resetClock() {
        this.clock = Clock.systemDefaultZone()
    }

    private fun isValidTimeRange(): Boolean {
        val now = LocalTime.now(clock)
        val start = LocalTime.of(17, 0)
        val end = LocalTime.of(22, 0)
        return now.isAfter(start) && now.isBefore(end)
    }

    fun validateMessageSendTime() {
        require(isValidTimeRange()) {
            "이미 10시가 지나서 쪽지를 예약할 수 없어요\n" +
                "아쉽지만 내일 다시 작성해보는 건 어떨까요?"
        }
    }

    fun validateMessageUpdateTime() {
        require(isValidTimeRange()) {
            "이미 10시가 지나서 쪽지를 수정할 수 없어요\n" +
                "아쉽지만 내일 다시 작성해보는 건 어떨까요?"
        }
    }
}
