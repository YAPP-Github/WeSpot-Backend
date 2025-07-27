package com.wespot.post.nudge.server_driven

import com.wespot.post.nudge.VoteNudge
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.text.RichTextV2

data class VoteComponent(
    val id: Long,
    val type: String = "VoteComponent",
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
                        backgroundColor = Color("#FFFFFF"),
                        text = RichTextV2(text = "비밀 투표")
                    ),
                    text = RichTextV2(text = "지금 우리 반에서 가장\n${voteOption.content} 친구는?"),
                    actionIcon = VoteContent.VoteActionIcon(
                        backgroundColor = Color("#FFFFFF"),
                        icon = IconV2.MOVE_TO_VOTE_ICON
                    ),
                    gradation = Gradation(
                        startColor = Color("#FFFFFF"),
                        endColor = Color("#FFFFFF"),
                        angle = 1
                    )
                )
            )
        }

    }

}
