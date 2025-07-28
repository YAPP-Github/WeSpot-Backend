package com.wespot.post.nudge.server_driven

import com.wespot.common.view.ColorType
import com.wespot.common.view.TypographType
import com.wespot.post.Post
import com.wespot.post.nudge.HotPostNudge
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.image.ImageContentV2
import com.wespot.view.text.RichTextV2

class HotPostComponent(
    val type: String = "HotPostItem",
    val id: Long,
    val content: HotPostContent
) {

    data class HotPostContent(
        val title: HotPostTitle,
        val posts: List<HotPost>
    ) {

        data class HotPostTitle(
            val icon: IconV2 = IconV2.HOT_POST_ICON,
            val text: RichTextV2 = RichTextV2.HOT_POST_TEXT,
        ) {

        }

        data class HotPost(
            val headerSection: HotPostHeaderSection,
            val infoSection: HotPostInfoSection,
            val createdAt: RichTextV2,
            val gradation: Gradation,
        ) {

            companion object {

                fun from(post: Post): HotPost {
                    return HotPost(
                        headerSection = HotPostHeaderSection(
                            profileImage = ImageContentV2(
                                url = post.profile.url,
                                width = 24,
                                height = 24
                            ),
                            nickname = RichTextV2(
                                text = post.profile.name,
                                color = Color(value = ColorType.GRAY900.value),
                                typography = TypographType.BADGE.value,
                                maxLine = 1
                            ),
                        ),
                        infoSection = HotPostInfoSection(
                            title = post.title?.let {
                                RichTextV2(
                                    text = it.content,
                                    color = Color(value = ColorType.GRAY900.value),
                                    typography = TypographType.BODY05.value,
                                    maxLine = 1
                                )
                            },
                            description = RichTextV2(
                                text = post.description.content,
                                color = Color(value = ColorType.GRAY900.value),
                                typography = post.title?.let { TypographType.BODY07.value }
                                    ?: TypographType.BODY05.value,
                                maxLine = post.title?.let { 1 } ?: 2
                            ),
                        ),
                        createdAt = RichTextV2(
                            text = post.createdAt.toString(),
                            color = Color(value = ColorType.GRAY500.value),
                            typography = TypographType.BODY12.value,
                            maxLine = 1
                        ),
                        gradation = Gradation(
                            startColor = Color(value = "#F6D1FF", type = Color.HEX),
                            endColor = Color(value = "#A7A7FF", type = Color.HEX),
                            angle = 90
                        )
                    )
                }

            }

            data class HotPostHeaderSection(
                val profileImage: ImageContentV2,
                val nickname: RichTextV2,
            ) {

            }

            data class HotPostInfoSection(
                val title: RichTextV2?,
                val description: RichTextV2,
            ) {

            }

        }

    }

    companion object {

        fun of(id: Long, hotPostNudgeContent: HotPostNudge.HotPostNudgeContent): HotPostComponent {
            return HotPostComponent(
                id = id,
                content = HotPostContent(
                    title = HotPostContent.HotPostTitle(),
                    posts = hotPostNudgeContent.posts
                        .map { HotPostContent.HotPost.from(post = it) }
                )
            )
        }

    }

}
