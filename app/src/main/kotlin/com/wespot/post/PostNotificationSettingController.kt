package com.wespot.post

import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/post")
class PostNotificationSettingController(
    private val postNotificationUseCase: PostNotificationUseCase
) {

    @PatchMapping("/{postId}/notification/comment")
    fun updatePostNotificationSettingForComment(postId: Long): String {

    }

}
