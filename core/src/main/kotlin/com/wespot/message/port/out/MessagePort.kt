package com.wespot.message.port.out


interface MessagePort {

    fun deleteById(id: Long)

    fun existsByIdAndSenderIdAndReceiverId(id: Long, senderId: Long, receiverId: Long): Boolean

}
