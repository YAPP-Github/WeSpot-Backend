package com.wespot.post

import com.wespot.common.EverProfile
import java.time.LocalDateTime

data class PostProfile(
    val id: Long = 0L,
    val userId: Long,
    val url: String = EverProfile.randomProfile().profileUrl,
    val name: String = INITIAL_NAME,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    companion object {

        val DEFAULT_PROFILE: PostProfile = PostProfile(
            userId = 0L,
        )

        private const val INITIAL_NAME = "익명의 글쓴이"

        fun from(userId: Long): PostProfile {
            return PostProfile(
                userId = userId,
            )
        }

    }

}
