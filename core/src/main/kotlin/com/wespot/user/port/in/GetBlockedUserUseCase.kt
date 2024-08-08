package com.wespot.user.port.`in`

import com.wespot.user.block.BlockedUser

interface GetBlockedUserUseCase {

    fun findAllByBlockerId(blockerId: Long): List<BlockedUser>


}
