package com.wespot.post.nudge.server_driven

import com.wespot.common.view.ColorType
import com.wespot.common.view.TypographType
import com.wespot.post.nudge.VoteNudge
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.text.RichTextV2

data class VoteComponent(
    val id: Long,
    val type: String = "VoteItem",
    val content: VoteContent,
) {

    data class VoteContent(
        val badge: VoteBadge,
        val text: RichTextV2,
        val actionIcon: VoteActionIcon,
        val gradation: Gradation,
    ) {

        data class VoteBadge(
            val backgroundColor: Color,
            val text: RichTextV2,
        )

        data class VoteActionIcon(
            val backgroundColor: Color,
            val icon: IconV2,
        )
    }

    companion object {

        fun of(id: Long, voteNudgeContent: VoteNudge.VoteNudgeContent): VoteComponent {
            val voteOption = voteNudgeContent.voteOption

            return VoteComponent(
                id = id,
                content = VoteContent(
                    badge = VoteContent.VoteBadge(
                        backgroundColor = Color("#FF8D65", type = "Hex"),
                        text = RichTextV2(
                            text = "비밀 투표",
                            color = Color(value = ColorType.WHITE.value),
                            typography = TypographType.BADGE.value,
                            maxLine = 1
                        )
                    ),
                    text = RichTextV2(
                        text = "지금 우리 반에서 가장\n${voteOption.content.content} 친구는?",
                        color = Color(value = ColorType.GRAY900.value),
                        typography = TypographType.BODY04.value,
                        maxLine = 2
                    ),
                    actionIcon = VoteContent.VoteActionIcon(
                        backgroundColor = Color(value = ColorType.GRAY900.value),
                        icon = IconV2.MOVE_TO_VOTE_ICON
                    ),
                    gradation = Gradation(
                        startColor = Color(value = "#FEF0B5", type = Color.HEX),
                        endColor = Color(value = "#FBAA8B", type = Color.HEX),
                        angle = 90
                    )
                )
            )
        }

    }

}
