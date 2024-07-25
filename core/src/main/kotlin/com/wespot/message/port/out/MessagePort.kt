package com.wespot.message.port.out


interface MessagePort {

    fun deleteById(id: Long)

    fun existsById(id: Long): Boolean

}
