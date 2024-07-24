package com.wespot.user

import com.wespot.user.dto.response.UserListResponse
import com.wespot.user.port.`in`.SearchUserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/search")
class SearchUserController(
    private val searchUserUseCase: SearchUserUseCase
) {

    @GetMapping
    fun searchUser(
        @RequestParam name: String,
        @RequestParam(required = false) cursorId: Long?,
    ): ResponseEntity<UserListResponse> {
        val response = searchUserUseCase.searchUsers(
            keyword = name,
            cursorId = cursorId ?: 0
        )

        return ResponseEntity.ok()
            .body(response)
    }
}
