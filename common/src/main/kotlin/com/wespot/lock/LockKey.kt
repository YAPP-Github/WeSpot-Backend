package com.wespot.lock

import com.wespot.exception.CustomException
import org.springframework.http.HttpStatus


@JvmRecord
data class LockKey(val keywords: List<String>) {

    init {
        if (keywords.isEmpty()) {
            throw CustomException(
                message = "Lock Key는 최소 1개 이상의 Keyword를 가져야 합니다.",
                status = HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    constructor(vararg keywords: Any?) : this(
        keywords
            .filterNotNull()
            .map { it.toString() }
            .toList()
    )

    val key: String
        get() {
            return keywords.joinToString(":") { it }
                .hashCode()
                .toString()
        }
}
