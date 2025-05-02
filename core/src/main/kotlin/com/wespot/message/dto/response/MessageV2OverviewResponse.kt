package com.wespot.message.dto.response

import com.wespot.message.v2.MessageRoom
import java.time.LocalDateTime

data class MessageV2OverviewResponse( // TODO : 문서 변경
    val id: Long,
    val isMeMessageRoomOwner: Boolean,
    val thumbnail: String,
    val isExistsUnreadMessage: Boolean,
    val latestChatTime: LocalDateTime,
    val isAnonymous: Boolean,
    val name: String,
    val schoolName: String?,
    val grade: Int?,
    val classNumber: Int?,
    val isBookmarked: Boolean,
    val isReported: Boolean,
    val isBlocked: Boolean,
    val isEver: Boolean
) {

    companion object {

        fun from(room: MessageRoom): MessageV2OverviewResponse {
            return MessageV2OverviewResponse(
                id = room.id(),
                isMeMessageRoomOwner = room.isViewerOwnerOfMessageRoom(),
                thumbnail = room.receiverProfileImage(),
                isExistsUnreadMessage = room.isExistsUnReadMessage(),
                latestChatTime = room.latestChatTime(),
                isAnonymous = room.isAnonymous(),
                name = room.receiverName(),
                schoolName = room.receiverSchoolName(),
                grade = room.receiverGrade(),
                classNumber = room.receiverClassNumber(),
                isBookmarked = room.isBookmarked(),
                isReported = room.isReported(),
                isBlocked = room.isBlocked(),
                isEver = room.isReceiverEver()
            )
        }

    }

}
