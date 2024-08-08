package com.wespot.user

import com.wespot.user.dto.response.BlockedUserResponse
import com.wespot.user.port.`in`.BlockedUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/messages")
class BlockedUserController(
    private val blockedUserUseCase: BlockedUserUseCase
) {

    @PostMapping("/{messageId}/block")
    fun blockUser(@PathVariable messageId: Long): ResponseEntity<BlockedUserResponse> {
        val blockedUser = blockedUserUseCase.blockedUser(messageId)
        return ResponseEntity.ok().body(blockedUser)
    }

    @PostMapping("/{messageId}/unblock")
    fun unblockUser(@PathVariable messageId: Long): ResponseEntity<Unit> {
        blockedUserUseCase.unblockedUser(messageId)
        return ResponseEntity.noContent().build()
    }

}
