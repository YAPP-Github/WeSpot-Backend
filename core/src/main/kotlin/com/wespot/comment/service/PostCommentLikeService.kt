package com.wespot.comment.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.PostCommentLike
import com.wespot.comment.port.`in`.PostCommentLikeUseCase
import com.wespot.comment.port.out.PostCommentLikePort
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.exception.CustomException
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostCommentLikeService(
    private val userPort: UserPort,
    private val postCommentLikePort: PostCommentLikePort,
    private val postCommentPort: PostCommentPort
) : PostCommentLikeUseCase {

    @Transactional
    override fun likeComment(commentId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseCommunity()) {
            throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
        }

        val postComment = postCommentPort.findById(commentId) ?: throw CustomException(message = "존재하지 않는 댓글입니다.")
        postCommentLikePort.findByPostCommentIdAndUserId(postCommentId = commentId, userId = loginUser.id)
            ?.let {
                postCommentLikePort.deleteById(it.id)
                val removedLikePostComment = postComment.removeLike()
                postCommentPort.save(removedLikePostComment)
            }
            ?: run {
                val postCommentLike = PostCommentLike(postCommentId = commentId, userId = loginUser.id)
                postCommentLikePort.save(postCommentLike)
                val addedLikePostComment = postComment.addLike()
                postCommentPort.save(addedLikePostComment)
            }
    }

}
