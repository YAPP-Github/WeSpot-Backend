package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.comment.port.out.PostCommentPort
import com.wespot.common.dto.PostPagingResponse
import com.wespot.common.dto.view.HotPostComponentResponse
import com.wespot.common.dto.view.ImageContentV2Response
import com.wespot.common.dto.view.MessageComponentResponse
import com.wespot.common.dto.view.VoteComponentResponse
import com.wespot.exception.CustomException
import com.wespot.post.Post
import com.wespot.post.PostCategory
import com.wespot.post.dto.response.PostComponentResponse
import com.wespot.post.nudge.HotPostNudge
import com.wespot.post.nudge.MessageNudge
import com.wespot.post.nudge.NudgeItem
import com.wespot.post.nudge.VoteNudge
import com.wespot.post.nudge.server_driven.HotPostComponent
import com.wespot.post.nudge.server_driven.MessageComponent
import com.wespot.post.nudge.server_driven.VoteComponent
import com.wespot.post.port.`in`.PostInquiryUseCase
import com.wespot.post.port.`in`.PostNudgeUseCase
import com.wespot.post.port.out.PostCategoryPort
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostScrapPort
import com.wespot.post.server_driven.PostComponent
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostInquiryService(
    private val userPort: UserPort,
    private val postCategoryPort: PostCategoryPort,
    private val postPort: PostPort,
    private val postCommentPort: PostCommentPort,
    private val postScrapPort: PostScrapPort,

    private val nudgeModalUseCase: PostNudgeUseCase,
) : PostInquiryUseCase {

    @Transactional(readOnly = false)
    override fun findPostsByCategoryId(
        categoryId: Long,
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postCategory = postCategoryPort.findById(categoryId) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 카테고리입니다."
        )

        val posts = postPort.findAllByCategoryId(
            categoryId = postCategory.id,
            viewerId = loginUser.id,
            cursorId = cursorId,
            inquirySize = inquirySize + 1
        )

        val data = posts.map { PostComponent.of(it, viewerId = loginUser.id, isCategoryScreen = true) }
            .map { PostComponentResponse.from(it) }
        val lastCursorId = data.minOfOrNull { it.id }
        val hasNext = posts.size == (inquirySize.toInt() + 1)

        return PostPagingResponse(
            data = data.take(inquirySize.toInt()),
            background = ImageContentV2Response(url = postCategory.backgroundImage),
            thumbnail = ImageContentV2Response(url = postCategory.thumbnail),
            lastCursorId = lastCursorId,
            hasNext = hasNext
        )
    }

    private fun postPagingResponse(
        posts: List<Post>,
        inquirySize: Long,
        viewerId: Long,
    ): PostPagingResponse {
        val data = posts.map { PostComponent.of(it, viewerId = viewerId) }
            .map { PostComponentResponse.from(it) }
        val lastCursorId = data.minOfOrNull { it.id }
        val hasNext = posts.size == (inquirySize.toInt() + 1)

        return PostPagingResponse(data = data.take(inquirySize.toInt()), lastCursorId = lastCursorId, hasNext = hasNext)
    }

    @Transactional(readOnly = false)
    override fun findPostsByMajorCategoryName(
        majorCategoryName: String,
        countOfPostsViewed: Long,
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val posts = findPostsByCategoryName(
            majorCategoryName = majorCategoryName,
            loginUser = loginUser,
            inquirySize = inquirySize,
            cursorId = cursorId
        )

        val startSequence = countOfPostsViewed.toInt() + 1
        val endSequence = countOfPostsViewed.toInt() + inquirySize.toInt()
        val nudges = nudgeModalUseCase.findAllNudgeModalsBySequence(
            startSequence = startSequence,
            endSequence = endSequence,
        ).sortedBy { it.mustViewSequence() }

        val data = mixPostAndNudge(startSequence, endSequence, posts.take(inquirySize.toInt()), nudges, loginUser.id)
        val lastCursorId = posts.minOfOrNull { it.id }
        val hasNext = posts.size == (inquirySize.toInt() + 1)

        return PostPagingResponse(
            data = data,
            lastCursorId = lastCursorId,
            hasNext = hasNext

        )
    }

    private fun findPostsByCategoryName(
        majorCategoryName: String,
        loginUser: User,
        inquirySize: Long,
        cursorId: Long?
    ): List<Post> {
        if (majorCategoryName == PostCategory.ALL_INCLUDE_CATEGORY_NAME || majorCategoryName == "") {
            return postPort.findAllRecentPost(
                viewerId = loginUser.id,
                inquirySize = inquirySize + 1,
                cursorId = cursorId
            )
        }

        val categoryIds = postCategoryPort.findAllByMajorCategoryName(majorCategoryName)
            .map { it.id }
        return postPort.findAllByCategoryIdIn(
            categoryIds = categoryIds,
            viewerId = loginUser.id,
            inquirySize = inquirySize + 1,
            cursorId = cursorId
        )
    }

    private fun mixPostAndNudge(
        startSequence: Int,
        endSequence: Int,
        posts: List<Post>,
        nudges: List<NudgeItem>,
        viewerId: Long,
    ): MutableList<Any> {
        val data = mutableListOf<Any>()
        var nudgesIndex = 0

        (startSequence..endSequence).asSequence().forEach { i ->
            val post = posts[i - startSequence]
            val component = PostComponent.of(post, viewerId)
            data.add(PostComponentResponse.from(component))

            if (nudges.size > nudgesIndex && nudges[nudgesIndex].mustViewSequence() == i) {
                data.add(convertNudgeResponse(nudges, nudgesIndex++)!!)
            }
        }

        return data
    }

    private fun convertNudgeResponse(
        nudges: List<NudgeItem>,
        nudgesIndex: Int,
    ): Any? {
        val nudgeItem = nudges[nudgesIndex]
        val clazz = nudgeItem.nudgeType().clazz
        val content = nudgeItem.content()

        return when (clazz) {
            VoteNudge.VoteNudgeContent::class.java -> {
                val element = VoteComponent.of(
                    Long.MAX_VALUE + nudgesIndex,
                    content as VoteNudge.VoteNudgeContent
                )
                VoteComponentResponse.from(element)
            }

            MessageNudge.MessageNudgeContent::class.java -> {
                val element = MessageComponent.from(Long.MAX_VALUE + nudgesIndex)
                MessageComponentResponse.from(element)
            }

            HotPostNudge.HotPostNudgeContent::class.java -> {
                val element = HotPostComponent.of(
                    Long.MAX_VALUE + nudgesIndex,
                    content as HotPostNudge.HotPostNudgeContent
                )
                HotPostComponentResponse.from(element)
            }

            else -> {
                null
            }
        }
    }

    @Transactional(readOnly = false)
    override fun findPostById(
        postId: Long,
    ): PostComponentResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId, viewerId = loginUser.id) ?: throw CustomException(
            status = HttpStatus.BAD_REQUEST,
            message = "존재하지 않는 게시글입니다."
        )

        val postComponent = PostComponent.fromDetail(post, loginUser.id)
        return PostComponentResponse.from(postComponent)
    }

    @Transactional(readOnly = false)
    override fun findCommentedPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postIds = postCommentPort.findAllByUserId(userId = loginUser.id)
            .map { it.postId }
            .distinct()

        val posts = postPort.findAllByPostIdIn(
            postIds = postIds,
            viewerId = loginUser.id,
            inquirySize = inquirySize + 1,
            cursorId = cursorId
        )

        return postPagingResponse(posts, inquirySize, loginUser.id)
    }

    @Transactional(readOnly = false)
    override fun findScrappedPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postIds = postScrapPort.findAllByUserId(userId = loginUser.id)
            .map { it.postId }
            .distinct()

        postPort.findAllByPostIdIn(
            postIds = postIds,
            viewerId = loginUser.id,
            inquirySize = inquirySize + 1,
            cursorId = cursorId
        ).let { posts ->
            return postPagingResponse(posts, inquirySize, loginUser.id)
        }
    }

    @Transactional(readOnly = false)
    override fun findWrittenPosts(
        inquirySize: Long,
        cursorId: Long?,
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        postPort.findAllByUserId(
            authorId = loginUser.id,
            inquirySize = inquirySize + 1,
            cursorId = cursorId
        ).let { return postPagingResponse(it, inquirySize, loginUser.id) }
    }


}
