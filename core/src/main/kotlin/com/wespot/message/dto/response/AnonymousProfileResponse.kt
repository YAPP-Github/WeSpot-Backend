package com.wespot.message.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.wespot.message.v2.UserProfile
import java.time.LocalDateTime

data class AnonymousProfileResponse(
    val id: Long,
    val image: String,
    val name: String,
    val isAnonymous: Boolean,
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    val recentlyTalk: LocalDateTime?,
    val myTurnToAnswer: Boolean
) {

    companion object {
        fun from(
            userProfile: UserProfile
        ): AnonymousProfileResponse {
            return AnonymousProfileResponse(
                id = userProfile.profileId,
                image = userProfile.image,
                name = userProfile.name,
                isAnonymous = userProfile.isAnonymous,
                recentlyTalk = userProfile.recentlyTalk(),
                myTurnToAnswer = userProfile.isAbleToAnswer()
            )
        }
    }

}
