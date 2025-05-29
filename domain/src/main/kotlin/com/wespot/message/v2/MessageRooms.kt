package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import org.springframework.http.HttpStatus

data class MessageRooms(
    val rooms: List<MessageRoom>
) {

    companion object {

        fun createOverview(viewer: User, rooms: List<MessageV2>, messageDetails: List<MessageV2>): MessageRooms {
            val roomIdToMessages: Map<Long, List<MessageV2>> = messageDetails
                .filter { it.messageRoomId != null }
                .groupBy { it.messageRoomId!! }

            val resultOfRooms = rooms.map { roomMessage ->
                val messages = roomIdToMessages[roomMessage.messageRoomId] ?: emptyList()
                MessageRoom.of(viewer = viewer, roomMessage = roomMessage, messages = messages)
            }

            return MessageRooms(resultOfRooms)
        }

        fun create() {
        }

    }

    fun asList(): List<MessageRoom> {
        return rooms
    }

    fun viewer(): User? {
        val viewerSet = rooms.map { it.viewer }
            .toSet()

        if (viewerSet.size > 1) {
            throw CustomException(
                message = "조회자는 무조건 1명을 초과할 수 없습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }
        return viewerSet.firstOrNull()
    }

}
