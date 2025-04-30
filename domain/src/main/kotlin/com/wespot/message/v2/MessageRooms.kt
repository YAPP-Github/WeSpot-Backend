package com.wespot.message.v2

import com.wespot.user.User

data class MessageRooms(
    val rooms: List<MessageRoom>
) {

    companion object {

        fun createOverview(user: User, rooms: List<MessageV2>, messageDetails: List<MessageV2>): MessageRooms {
            val roomIdToMessages: Map<Long, List<MessageV2>> = messageDetails
                .filter { it.messageRoomId != null }
                .groupBy { it.messageRoomId!! }

            val resultOfRooms = rooms.map { roomMessage ->
                val messages = roomIdToMessages[roomMessage.messageRoomId] ?: emptyList()
                MessageRoom.of(viewer = user, roomMessage = roomMessage, messages = messages)
            }

            return MessageRooms(resultOfRooms)
        }

        fun create() {
        }

    }

    fun asList(): List<MessageRoom> {
        return rooms
    }

}
