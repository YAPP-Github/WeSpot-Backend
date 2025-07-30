package com.wespot.post.server_driven

import com.wespot.common.TimeExpressionUtil
import com.wespot.common.view.ColorType
import com.wespot.common.view.TypographType
import com.wespot.post.Post
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.image.ImageContentV2
import com.wespot.view.text.RichTextV2

data class PostComponent(
    val id: Long,
    val type: String = "PostItem",
    val content: PostContent,
) {

    data class PostContent(
        val category: PostCategoryComponent? = null,
        val headerSection: PostHeaderSection,
        val infoSection: PostInfoSection,
        val contentSection: PostContentSection? = null,
        val footerSection: PostFooterSection,
        val button: PostButtonComponent? = null,
    ) {

        data class PostHeaderSection(
            val profileImage: ImageContentV2,
            val nickname: RichTextV2,
            val createdAt: RichTextV2,
            val category: PostCategoryComponent? = null,
            val button: PostButtonComponent? = null,
        ) {

        }

        data class PostInfoSection(
            val title: RichTextV2? = null,
            val description: RichTextV2,
            val seeMore: RichTextV2 = RichTextV2(
                text = "더보기",
                color = Color(value = ColorType.PRIMARY300.value),
                typography = TypographType.BODY06.value,
                maxLine = 1
            ),
            val maxLine: Int = 5,
        ) {

        }

        data class PostContentSection(
            val type: String = "Images",
            val images: List<ImageContentV2>,
        ) {

        }

        data class PostFooterSection(
            val reactions: List<PostReactionItem>,
            val scrap: PostScrapComponent
        ) {
            data class PostReactionItem(
                val type: String,
                val icon: IconV2,
                val count: RichTextV2,
                val selected: Boolean,
            ) {

            }

            data class PostScrapComponent(
                val icon: IconV2,
                val selected: Boolean = false,
            ) {

            }

        }

        data class PostButtonComponent(
            val type: String = "notification",
            val icon: IconV2,
            val text: RichTextV2,
            val isSelected: Boolean = false,
        ) {

        }

    }

    companion object {

        fun from(post: Post): PostComponent {
            return PostComponent(
                id = post.id,
                content = PostContent(
                    headerSection = PostContent.PostHeaderSection(
                        profileImage = ImageContentV2(
                            url = post.profile.url,
                            width = 36,
                            height = 36,
                        ),
                        nickname = RichTextV2(
                            text = post.profile.name,
                            color = Color(value = ColorType.WHITE.value),
                            typography = TypographType.BODY06.value,
                            maxLine = 1,
                        ),
                        createdAt = RichTextV2(
                            text = TimeExpressionUtil.postTime(createdAt = post.createdAt),
                            color = Color(value = ColorType.GRAY400.value),
                            typography = TypographType.BADGE.value,
                            maxLine = 1,
                        ),
                        category = PostCategoryComponent(
                            text = RichTextV2(
                                text = post.category.name,
                                color = Color(value = ColorType.GRAY300.value),
                                typography = TypographType.BADGE.value,
                                maxLine = 1,
                            ),
                            target = post.category.majorCategoryName,
                            icon = IconV2(
                                url = "https://dw2d2daekmyur.cloudfront.net/right_arrow.png",
                            )
                        )
                    ),
                    infoSection = PostContent.PostInfoSection(
                        title = post.title?.let {
                            RichTextV2(
                                text = post.title.content,
                                color = Color(value = ColorType.WHITE.value),
                                typography = TypographType.BODY04.value,
                                maxLine = 1,
                            )
                        },
                        description = RichTextV2(
                            text = post.description.content,
                            color = Color(value = ColorType.WHITE.value),
                            typography = TypographType.BODY00.value,
                            maxLine = post.title?.let { 3 } ?: 5,
                        ),
                    ),
                    contentSection = post.images?.let {
                        PostContent.PostContentSection(
                            images = it.postImages.map { image ->
                                ImageContentV2(url = image.url)
                            }
                        )
                    },
                    footerSection = PostContent.PostFooterSection(
                        reactions = listOf(
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Chat",
                                icon = IconV2(
                                    url = "https://dw2d2daekmyur.cloudfront.net/TALK_BALLON.png"
                                ),
                                count = RichTextV2(
                                    text = post.commentCount.toString(),
                                    color = Color(value = ColorType.GRAY300.value),
                                    typography = TypographType.BADGE.value,
                                    maxLine = 1,
                                ),
                                selected = false,
                            ),
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Like",
                                icon = IconV2(
                                    url = "https://dw2d2daekmyur.cloudfront.net/THUMBS_UP.png",
                                ),
                                count = RichTextV2(
                                    text = post.likeCount.toString(),
                                    color = Color(value = ColorType.GRAY300.value),
                                    typography = TypographType.BADGE.value,
                                    maxLine = 1,
                                ),
                                selected = post.postStatusByViewer?.isViewerPushedLike ?: false,
                            ),
                        ),
                        scrap = PostContent.PostFooterSection.PostScrapComponent(
                            icon = IconV2(
                                url = "https://dw2d2daekmyur.cloudfront.net/BOOKMARK.png",
                            ),
                            selected = post.postStatusByViewer?.isViewerPushedScrap ?: false,
                        ),
                    )
                ),
            )
        }

        fun fromDetail(post: Post): PostComponent {
            return PostComponent(
                id = post.id,
                content = PostContent(
                    category = PostCategoryComponent(
                        text = RichTextV2(
                            text = post.category.name,
                            color = Color(value = "#FFFFFF", type = Color.HEX),
                            typography = TypographType.BODY06.value,
                            maxLine = 1,
                        ),
                        target = post.category.id.toString(),
                        icon = IconV2(
                            url = "https://dw2d2daekmyur.cloudfront.net/right_arrow_in_black.png",
                        )
                    ),
                    headerSection = PostContent.PostHeaderSection(
                        profileImage = ImageContentV2(
                            url = post.profile.url,
                            width = 40,
                            height = 40,
                        ),
                        nickname = RichTextV2(
                            text = post.profile.name,
                            color = Color(value = ColorType.WHITE.value),
                            typography = TypographType.BODY06.value,
                            maxLine = 1
                        ),
                        createdAt = RichTextV2(
                            text = TimeExpressionUtil.postTime(createdAt = post.createdAt),
                            color = Color(value = ColorType.GRAY400.value),
                            typography = TypographType.BODY09.value,
                            maxLine = 1
                        ),
                        button = PostContent.PostButtonComponent(
                            icon = IconV2(
                                url = "https://dw2d2daekmyur.cloudfront.net/comment_notification_check.png",
                            ),
                            text = RichTextV2(
                                text = "댓글 알림",
                                color = Color(value = ColorType.GRAY100.value),
                                typography = TypographType.BODY09.value,
                                maxLine = 1
                            ),
                            isSelected = post.postStatusByViewer?.isViewerPushedNotification ?: false,
                        )
                    ),
                    infoSection = PostContent.PostInfoSection(
                        title = post.title?.let {
                            RichTextV2(
                                text = post.title.content,
                                color = Color(value = ColorType.WHITE.value),
                                typography = TypographType.BODY04.value,
                                maxLine = 1,
                            )
                        },
                        description = RichTextV2(
                            text = post.description.content,
                            color = Color(value = ColorType.WHITE.value),
                            typography = TypographType.BODY00.value,
                            maxLine = post.title?.let { 3 } ?: 5,
                        ),
                    ),
                    contentSection = post.images?.let {
                        PostContent.PostContentSection(
                            images = it.postImages.map { image ->
                                ImageContentV2(
                                    url = image.url,
                                )
                            }
                        )
                    },
                    footerSection = PostContent.PostFooterSection(
                        reactions = listOf(
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Chat",
                                icon = IconV2(
                                    url = "https://dw2d2daekmyur.cloudfront.net/TALK_BALLON.png"
                                ),
                                count = RichTextV2(
                                    text = post.commentCount.toString(),
                                    color = Color(value = ColorType.GRAY300.value),
                                    typography = TypographType.BADGE.value,
                                    maxLine = 1,
                                ),
                                selected = false,
                            ),
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Like",
                                icon = IconV2(
                                    url = "https://dw2d2daekmyur.cloudfront.net/THUMBS_UP.png",
                                ),
                                count = RichTextV2(
                                    text = post.likeCount.toString(),
                                    color = Color(value = ColorType.GRAY300.value),
                                    typography = TypographType.BADGE.value,
                                    maxLine = 1,
                                ),
                                selected = post.postStatusByViewer?.isViewerPushedLike ?: false,
                            ),
                        ),
                        scrap = PostContent.PostFooterSection.PostScrapComponent(
                            icon = IconV2(
                                url = "https://dw2d2daekmyur.cloudfront.net/BOOKMARK.png",
                            ),
                            selected = post.postStatusByViewer?.isViewerPushedScrap ?: false,
                        ),
                    )
                ),
            )
        }

    }

}
