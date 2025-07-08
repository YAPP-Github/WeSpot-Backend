package com.wespot.post

import java.time.LocalDateTime

data class PostProfile(
    val id: Long = 0L,
    val userId: Long,
    val url: String,
    val name: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private const val INITIAL_NAME = "익명의 글쓴이"
        fun from(userId: Long): PostProfile { // TODO : 새로운 익명 프로필 이미지 주입 로직 추가
            return PostProfile(
                userId = userId,
                url = "",
                name = INITIAL_NAME
            )
        }
    }
}
