package com.wespot.post.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.common.dto.view.IconV2Response
import com.wespot.common.dto.view.ImageContentV2Response
import com.wespot.common.dto.view.RichTextV2Response
import com.wespot.post.server_driven.PostComponent

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PostComponentResponse(
    val id: Long,
    val type: String,
    val isMyPost: Boolean,
    val content: PostContentResponse,
) {

    data class PostContentResponse(
        val category: PostCategoryComponentResponse? = null,
        val headerSection: PostHeaderSectionResponse,
        val infoSection: PostInfoSectionResponse,
        val contentSection: PostContentSectionResponse? = null,
        val footerSection: PostFooterSectionResponse,
        val button: PostButtonComponentResponse? = null,
    ) {

        data class PostHeaderSectionResponse(
            val profileImage: ImageContentV2Response,
            val nickname: RichTextV2Response,
            val createdAt: RichTextV2Response,
            val category: PostCategoryComponentResponse? = null,
            val button: PostButtonComponentResponse? = null,
        ) {
            companion object {

                fun from(headerSection: PostComponent.PostContent.PostHeaderSection): PostHeaderSectionResponse {
                    return PostHeaderSectionResponse(
                        profileImage = ImageContentV2Response(
                            url = headerSection.profileImage.url,
                            width = headerSection.profileImage.width,
                            height = headerSection.profileImage.height,
                        ),
                        nickname = RichTextV2Response.from(headerSection.nickname),
                        createdAt = RichTextV2Response.from(headerSection.createdAt),
                        category = PostCategoryComponentResponse.from(headerSection.category),
                        button = PostButtonComponentResponse.from(headerSection.button)
                    )
                }

            }
        }

        data class PostInfoSectionResponse(
            val title: RichTextV2Response? = null,
            val description: RichTextV2Response,
            val seeMore: RichTextV2Response,
            val maxLine: Int,
        ) {
            companion object {

                fun from(postInfoSection: PostComponent.PostContent.PostInfoSection): PostInfoSectionResponse {
                    return PostInfoSectionResponse(
                        title = postInfoSection.title?.let { RichTextV2Response.from(postInfoSection.title!!) },
                        description = RichTextV2Response.from(postInfoSection.description),
                        seeMore = RichTextV2Response.from(postInfoSection.seeMore),
                        maxLine = postInfoSection.maxLine
                    )
                }

            }
        }

        data class PostContentSectionResponse(
            val type: String,
            val images: List<String>,
        ) {
            companion object {

                fun from(postContentSection: PostComponent.PostContent.PostContentSection?): PostContentSectionResponse? {
                    return postContentSection?.let {
                        PostContentSectionResponse(
                            type = it.type,
                            images = it.images
                        )
                    }
                }

            }
        }

        data class PostFooterSectionResponse(
            val reactions: List<ReactionItem>,
            val scrap: ScrapComponent,
        ) {
            data class ReactionItem(
                val type: String,
                val icon: IconV2Response,
                val count: RichTextV2Response,
                val selected: Boolean,
            )

            data class ScrapComponent(
                val icon: IconV2Response,
                val selected: Boolean = false,
            )

            companion object {

                fun from(postFooterSection: PostComponent.PostContent.PostFooterSection): PostFooterSectionResponse {
                    return PostFooterSectionResponse(
                        reactions = postFooterSection.reactions.map { reaction ->
                            ReactionItem(
                                type = reaction.type,
                                icon = IconV2Response.from(reaction.icon),
                                count = RichTextV2Response.from(reaction.count),
                                selected = reaction.selected
                            )
                        },
                        scrap = ScrapComponent(
                            icon = IconV2Response.from(postFooterSection.scrap.icon),
                            selected = postFooterSection.scrap.selected
                        )
                    )
                }

            }
        }

        data class PostButtonComponentResponse(
            val type: String = "notification",
            val icon: IconV2Response,
            val text: RichTextV2Response,
            val isSelected: Boolean = false,
        ) {
            companion object {

                fun from(postButtonComponent: PostComponent.PostContent.PostButtonComponent?): PostButtonComponentResponse? {
                    return postButtonComponent?.let {
                        PostButtonComponentResponse(
                            icon = IconV2Response.from(it.icon),
                            text = RichTextV2Response.from(it.text),
                            type = it.type,
                        )
                    }
                }

            }
        }
    }

    companion object {
        fun from(postComponent: PostComponent): PostComponentResponse {
            val content = postComponent.content
            return PostComponentResponse(
                id = postComponent.id,
                type = postComponent.type,
                isMyPost = postComponent.isMyPost,
                content = PostContentResponse(
                    category = PostCategoryComponentResponse.from(content.category),
                    headerSection = PostContentResponse.PostHeaderSectionResponse.from(content.headerSection),
                    infoSection = PostContentResponse.PostInfoSectionResponse.from(content.infoSection),
                    contentSection = PostContentResponse.PostContentSectionResponse.from(content.contentSection),
                    footerSection = PostContentResponse.PostFooterSectionResponse.from(content.footerSection),
                    button = PostContentResponse.PostButtonComponentResponse.from(content.button),
                )
            )
        }
    }

}
