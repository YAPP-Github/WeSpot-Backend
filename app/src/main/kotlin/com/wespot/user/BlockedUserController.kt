package com.wespot.user

import com.wespot.user.port.`in`.BlockedUserUseCase
import org.springframework.http.ResponseEntity
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
    fun blockUser(@PathVariable messageId: Long): ResponseEntity<Unit> {
        return if (blockedUserUseCase.blockedUser(messageId)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.noContent().build()
        }
    }

}
