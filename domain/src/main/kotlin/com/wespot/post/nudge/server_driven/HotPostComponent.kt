package com.wespot.post.nudge.server_driven

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
                                url = "",
                                width = 40,
                                height = 40
                            ), // TODO : 익명 프로필 추가하면 이거 넣음
                            nickname = RichTextV2(text = "익명의 글쓴이"),
                        ),
                        infoSection = HotPostInfoSection(
                            title = post.title?.let { RichTextV2(text = it.content) },
                            description = RichTextV2(
                                text = post.description.content,
                            ),
                        ),
                        createdAt = RichTextV2(
                            text = post.createdAt.toString(),
                        ),
                        gradation = Gradation(
                            startColor = Color("#FFFFFF"),
                            endColor = Color("#FFFFFF"),
                            angle = 1
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
