package com.wespot.message.dto.response

import com.wespot.message.v2.MessageRoom
import java.time.LocalDateTime

data class MessageV2OverviewResponse( // TODO : 문서 변경
    val id: Long,

    val senderProfile: MessageProfileResponse,

    val isMeMessageRoomOwner: Boolean,
    val isExistsUnreadMessage: Boolean,
    val latestChatTime: LocalDateTime,
    val isAnonymous: Boolean,

    val receiverProfile: MessageProfileResponse,

    val isBookmarked: Boolean,
    val isBlocked: Boolean,
    val isEver: Boolean
) {

    data class MessageProfileResponse(
        val iconUrl: String,
        val name: String,
        val schoolName: String?,
        val grade: Int?,
        val classNumber: Int?
    ) {

        companion object {
            fun of(
                iconUrl: String,
                name: String,
                schoolName: String?,
                grade: Int?,
                classNumber: Int?
            ): MessageProfileResponse {
                return MessageProfileResponse(
                    iconUrl = iconUrl,
                    name = name,
                    schoolName = schoolName,
                    grade = grade,
                    classNumber = classNumber
                )
            }
        }

    }

    companion object {

        fun from(room: MessageRoom): MessageV2OverviewResponse {
            return MessageV2OverviewResponse(
                id = room.id(),

                senderProfile = MessageProfileResponse.of(
                    iconUrl = room.senderProfileImage(),
                    name = room.senderName(),
                    schoolName = room.senderSchoolName(),
                    grade = room.senderGrade(),
                    classNumber = room.senderClassNumber()
                ),

                isMeMessageRoomOwner = room.isViewerOwnerOfMessageRoom(),
                isExistsUnreadMessage = room.isExistsUnReadMessage(),
                latestChatTime = room.latestChatTime(),
                isAnonymous = room.isReceiverUsingAnonymous(),
                receiverProfile = MessageProfileResponse.of(
                    iconUrl = room.receiverProfileImage(),
                    name = room.receiverName(),
                    schoolName = room.receiverSchoolName(),
                    grade = room.receiverGrade(),
                    classNumber = room.receiverClassNumber()
                ),

                isBookmarked = room.isBookmarked(),
                isBlocked = room.isBlocked(),
                isEver = room.isReceiverEver()
            )
        }

    }

}
