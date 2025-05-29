package com.wespot.message.v2

import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile
import java.time.LocalDateTime

data class UserProfile(
    val profileId: Long,
    val image: String,
    val name: String,
    val isAnonymous: Boolean,
    val messageRoom: MessageRoom?
) {

    companion object {

        private val EMPTY_MESSAGE_ROOM = null

        fun createByAnonymousProfile(
            anonymousProfile: AnonymousProfile,
            messageRoom: List<MessageRoom>
        ): UserProfile {
            return UserProfile(
                profileId = anonymousProfile.id,
                image = anonymousProfile.imageUrl,
                name = anonymousProfile.name,
                isAnonymous = true,
                messageRoom = messageRoom.find { it.isSameAnonymousProfile(anonymousProfile.id) }
            )
        }

        fun createByUserProfile(
            user: User?,
            messageRoom: List<MessageRoom>
        ): UserProfile? {
            if (user == null) {
                return null
            }

            return UserProfile(
                profileId = user.profile.id,
                image = user.profile.iconUrl,
                name = user.name,
                isAnonymous = false,
                messageRoom = messageRoom.find { it.isSameUserProfileAndNotAnonymous(viewer = user) }
            )
        }

    }

    fun recentlyTalk(): LocalDateTime? {
        if (messageRoom == EMPTY_MESSAGE_ROOM) {
            return null
        }

        return messageRoom.latestChatTime()
    }

    fun isAbleToAnswer(): Boolean {
        if (messageRoom == EMPTY_MESSAGE_ROOM) {
            return true
        }

        return messageRoom.isAbleToAnswer()
    }

}
