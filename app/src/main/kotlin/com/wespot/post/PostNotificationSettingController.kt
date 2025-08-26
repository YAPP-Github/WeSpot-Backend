package com.wespot.post

import com.wespot.post.port.`in`.PostNotificationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/post")
class PostNotificationSettingController(
    private val postNotificationUseCase: PostNotificationUseCase
) {

    @PostMapping("/{postId}/notification/comment")
    fun updatePostNotificationSettingForComment(@PathVariable postId: Long): ResponseEntity<Unit> {
        postNotificationUseCase.toggleNotification(postId)

        return ResponseEntity.status(HttpStatus.CREATED)
            .build()
    }

}
