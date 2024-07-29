package com.wespot.user.port.`in`

interface GetBlockedUserUseCase {

    fun findAllByBlockerId(blockerId: Long): List<Long>


}
