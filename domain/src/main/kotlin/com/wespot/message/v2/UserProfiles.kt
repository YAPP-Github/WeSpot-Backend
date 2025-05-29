package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import org.springframework.http.HttpStatus

data class UserProfiles(
    val userProfiles: List<UserProfile>
) {

    companion object {

        fun of(viewer: User, messageRooms: MessageRooms): UserProfiles {
            val isContainsOwnerIsNotViewer = messageRooms.asList()
                .any { !it.isViewerOwnerOfMessageRoom() }
            if (isContainsOwnerIsNotViewer) {
                throw CustomException(
                    message = "익명 프로필을 조회한자가 소유한 쪽지방이 아닌 쪽지를 가져왔습니다.",
                    status = HttpStatus.BAD_REQUEST,
                    view = ExceptionView.TOAST,
                )
            }

            val anonymousProfiles = messageRooms.asList()
                .filter { it.isMeUsingAnonymous() }
                .map { it.anonymousProfile()!! }

            val resultOfUserProfiles = anonymousProfiles.map { anonymousProfile ->
                UserProfile.createByAnonymousProfile(
                    anonymousProfile = anonymousProfile,
                    messageRoom = messageRooms.asList()
                )
            } + UserProfile.createByUserProfile(
                user = viewer,
                messageRoom = messageRooms.asList()
            )

            return UserProfiles(
                userProfiles = resultOfUserProfiles.filterNotNull()
                    .sortedBy { it.recentlyTalk() }
                    .reversed()
            )
        }

    }

    fun asList(): List<UserProfile> {
        return userProfiles
    }

}
