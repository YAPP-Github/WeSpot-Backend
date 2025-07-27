package com.wespot.post.server_driven

import com.wespot.post.Post
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
            val seeMore: RichTextV2 = RichTextV2(text = "더보기"),
            val maxLine: Int = 3,
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

        private const val AUTHOR_NAME = "익명의 글쓴이"

        fun from(post: Post): PostComponent {
            return PostComponent(
                id = post.id,
                content = PostContent(
                    headerSection = PostContent.PostHeaderSection(
                        profileImage = ImageContentV2(
                            url = "",
                            width = 40,
                            height = 40,
                        ), // TODO : 익명 프로필 나오면 레츠고
                        nickname = RichTextV2(
                            text = AUTHOR_NAME,
                        ),
                        createdAt = RichTextV2(
                            text = post.createdAt.toString(),
                        ),
                        category = PostCategoryComponent(
                            text = RichTextV2(
                                text = post.category.name,
                            ),
                            target = post.category.majorCategoryName,
                            icon = IconV2(
                                url = "",
                            )
                        )
                    ),
                    infoSection = PostContent.PostInfoSection(
                        title = post.title?.let {
                            RichTextV2(
                                text = post.title.content,
                            )
                        },
                        description = RichTextV2(
                            text = post.description.content,
                        ),
                        maxLine = 3,
                    ),
                    contentSection = post.images?.let {
                        PostContent.PostContentSection(
                            images = it.postImages.map { image ->
                                ImageContentV2(
                                    url = image.url,
                                    width = image.width,
                                    height = image.height,
                                )
                            }
                        )
                    },
                    footerSection = PostContent.PostFooterSection(
                        reactions = listOf(
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Chat",
                                icon = IconV2(
                                    url = ""
                                ),
                                count = RichTextV2(text = post.commentCount.toString()),
                                selected = false,
                            ),
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Like",
                                icon = IconV2(
                                    url = ""
                                ),
                                count = RichTextV2(text = post.likeCount.toString()),
                                selected = post.postStatusByViewer?.isViewerPushedLike ?: false,
                            ),
                        ),
                        scrap = PostContent.PostFooterSection.PostScrapComponent(
                            icon = IconV2(
                                url = "",
                            ),
                            selected = post.postStatusByViewer?.isViewerPushedScrap ?: false,
                        ),
                    )
                ),
            )
        }

        fun fromDetail( post: Post): PostComponent {
            return PostComponent(
                id = post.id,
                content = PostContent(
                    category = PostCategoryComponent(
                        text = RichTextV2(
                            text = post.category.name,
                        ),
                        target = post.category.id.toString(),
                        icon = IconV2(
                            url = "",
                        )
                    ),
                    headerSection = PostContent.PostHeaderSection(
                        profileImage = ImageContentV2(
                            url = "",
                            width = 40,
                            height = 40,
                        ), // TODO : 익명 프로필 나오면 레츠고
                        nickname = RichTextV2(
                            text = AUTHOR_NAME,
                        ),
                        createdAt = RichTextV2(
                            text = post.createdAt.toString(),
                        ),
                        button = PostContent.PostButtonComponent(
                            icon = IconV2(
                                url = "",
                            ),
                            text = RichTextV2(
                                text = "댓글 알림",
                            ),
                            isSelected = post.postStatusByViewer?.isViewerPushedNotification ?: false,
                        )
                    ),
                    infoSection = PostContent.PostInfoSection(
                        title = post.title?.let {
                            RichTextV2(
                                text = post.title.content,
                            )
                        },
                        description = RichTextV2(
                            text = post.description.content,
                        ),
                        maxLine = 3,
                    ),
                    contentSection = post.images?.let {
                        PostContent.PostContentSection(
                            images = it.postImages.map { image ->
                                ImageContentV2(
                                    url = image.url,
                                    width = image.width,
                                    height = image.height,
                                )
                            }
                        )
                    },
                    footerSection = PostContent.PostFooterSection(
                        reactions = listOf(
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Chat",
                                icon = IconV2(
                                    url = ""
                                ),
                                count = RichTextV2(text = post.commentCount.toString()),
                                selected = false,
                            ),
                            PostContent.PostFooterSection.PostReactionItem(
                                type = "Like",
                                icon = IconV2(
                                    url = ""
                                ),
                                count = RichTextV2(text = post.likeCount.toString()),
                                selected = post.postStatusByViewer?.isViewerPushedLike ?: false,
                            ),
                        ),
                        scrap = PostContent.PostFooterSection.PostScrapComponent(
                            icon = IconV2(
                                url = "",
                            ),
                            selected = post.postStatusByViewer?.isViewerPushedScrap ?: false,
                        ),
                    )
                ),
            )
        }

    }

}
