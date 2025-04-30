package com.wespot.message.port.out

import com.wespot.message.v2.MessageV2

interface MessageV2Port {

    fun save(messageV2: MessageV2): MessageV2

    fun countTodaySendMessages(userId: Long): Int

    fun findAllMessageRoomBySenderId(senderId: Long): List<MessageV2>

    fun findAllMessageRoomByReceiverId(receiverId: Long): List<MessageV2>

    fun findAllMessageRoomBySenderIdAndIsSenderBookmarkedTrue(senderId: Long): List<MessageV2>

    fun findAllMessageRoomByReceiverIdAndIsReceiverBookmarkedTrue(receiverId: Long): List<MessageV2>

    fun findAllLastMessageOfRoomByRoomIdIn(messageRoomIds: List<Long>): List<MessageV2>

    fun findById(id: Long): MessageV2

    fun findAllByMessageRoomId(messageRoomId: Long): List<MessageV2>

}
