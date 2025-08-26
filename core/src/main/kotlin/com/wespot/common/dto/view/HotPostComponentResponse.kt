package com.wespot.common.dto.view

import com.wespot.post.nudge.server_driven.HotPostComponent

class HotPostComponentResponse(
    val type: String,
    val id: Long,
    val content: HotPostContentResponse
) {

    data class HotPostContentResponse(
        val title: HotPostTitleResponse?,
        val posts: List<HotPostResponse>
    ) {

        data class HotPostTitleResponse(
            val icon: IconV2Response,
            val text: RichTextV2Response,
        ) {

        }

        data class HotPostResponse(
            val headerSection: HotPostHeaderSectionResponse,
            val infoSection: HotPostInfoSectionResponse,
            val createdAt: RichTextV2Response,
            val gradation: GradationResponse,
        ) {

            companion object {
            }

        }

        data class HotPostHeaderSectionResponse(
            val profileImage: ImageContentV2Response,
            val nickname: RichTextV2Response,
        ) {

        }

        data class HotPostInfoSectionResponse(
            val title: RichTextV2Response?,
            val description: RichTextV2Response,
        ) {

        }

    }

    companion object {

        fun from(hotPostComponent: HotPostComponent): HotPostComponentResponse {
            val title = hotPostComponent.content.title
            val posts = hotPostComponent.content.posts
            return HotPostComponentResponse(
                type = hotPostComponent.type,
                id = hotPostComponent.id,
                content = HotPostContentResponse(
                    title = HotPostContentResponse.HotPostTitleResponse(
                        icon = IconV2Response.from(title.icon),
                        text = RichTextV2Response.from(title.text)
                    ),
                    posts = posts.map { post ->
                        HotPostContentResponse.HotPostResponse(
                            headerSection = HotPostContentResponse.HotPostHeaderSectionResponse(
                                profileImage = ImageContentV2Response.from(post.headerSection.profileImage),
                                nickname = RichTextV2Response.from(post.headerSection.nickname)
                            ),
                            infoSection = HotPostContentResponse.HotPostInfoSectionResponse(
                                title = post.infoSection.title?.let { RichTextV2Response.from(it) },
                                description = RichTextV2Response.from(post.infoSection.description)
                            ),
                            createdAt = RichTextV2Response.from(post.createdAt),
                            gradation = GradationResponse.from(post.gradation)
                        )
                    }
                )
            )
        }
    }

}
