package com.wespot.message.port.out

import com.wespot.message.v2.MessageV2
import java.time.LocalDate

interface MessageV2Port {

    fun save(messageV2: MessageV2): MessageV2

    fun countTodaySendMessages(senderId: Long): Int

    fun findAllMessageRoomBySenderId(senderId: Long): List<MessageV2>

    fun findAllMessageRoomByReceiverId(receiverId: Long): List<MessageV2>

    fun findAllMessageRoomBySenderIdAndIsSenderBookmarkedTrue(senderId: Long): List<MessageV2>

    fun findAllMessageRoomByReceiverIdAndIsReceiverBookmarkedTrue(receiverId: Long): List<MessageV2>

    fun findAllMessageRoomBySenderIdAndIsSenderBlockedTrue(senderId: Long): List<MessageV2>

    fun findAllMessageRoomByReceiverIdAndIsReceiverBlockedTrue(receiverId: Long): List<MessageV2>

    fun findAllLastMessageOfRoomByRoomIdIn(messageRoomIds: List<Long>): List<MessageV2>

    fun findById(id: Long): MessageV2

    fun findAllByMessageRoomId(messageRoomId: Long): List<MessageV2>

    fun findAllMessageRoomBySenderIdAndReceiverId(senderId: Long, receiverId: Long): List<MessageV2>

    fun findAllLastMessageOfRoomByReceiverIdAndFromDate(receiverId: Long, from: LocalDate): List<MessageV2>

    fun isExistsBySenderIdAndReceiverIdWithRealName(senderId: Long, receiverId: Long): Boolean

}
