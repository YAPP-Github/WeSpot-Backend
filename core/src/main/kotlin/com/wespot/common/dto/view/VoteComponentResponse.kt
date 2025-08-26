package com.wespot.common.dto.view

import com.wespot.post.nudge.server_driven.VoteComponent

data class VoteComponentResponse(
    val id: Long,
    val type: String,
    val content: VoteContentResponse,
) {

    data class VoteContentResponse(
        val badge: VoteBadgeResponse,
        val text: RichTextV2Response,
        val actionIcon: VoteActionIconResponse,
        val gradation: GradationResponse,
    ) {

        data class VoteBadgeResponse(
            val backgroundColor: ColorResponse,
            val text: RichTextV2Response,
        ) {
            companion object {

                fun from(badge: VoteComponent.VoteContent.VoteBadge): VoteBadgeResponse {
                    return VoteBadgeResponse(
                        backgroundColor = ColorResponse.from(badge.backgroundColor),
                        text = RichTextV2Response.from(badge.text)
                    )
                }

            }
        }

        data class VoteActionIconResponse(
            val backgroundColor: ColorResponse,
            val icon: IconV2Response,
        ) {
            companion object {

                fun from(actionIcon: VoteComponent.VoteContent.VoteActionIcon): VoteActionIconResponse {
                    return VoteActionIconResponse(
                        backgroundColor = ColorResponse.from(actionIcon.backgroundColor),
                        icon = IconV2Response.from(actionIcon.icon)
                    )
                }

            }
        }
    }

    companion object {

        fun from(voteComponent: VoteComponent): VoteComponentResponse {
            return VoteComponentResponse(
                id = voteComponent.id,
                type = voteComponent.type,
                content = VoteContentResponse(
                    badge = VoteContentResponse.VoteBadgeResponse.from(voteComponent.content.badge),
                    text = RichTextV2Response.from(voteComponent.content.text),
                    actionIcon = VoteContentResponse.VoteActionIconResponse.from(voteComponent.content.actionIcon),
                    gradation = GradationResponse.from(voteComponent.content.gradation)
                )
            )
        }
    }

}
