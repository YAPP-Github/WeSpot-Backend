package com.wespot.post.nudge

import com.wespot.post.nudge.vo.NudgeType
import com.wespot.voteoption.VoteOption

data class VoteNudge(
    val mustViewSequence: Int,
    val content: VoteNudgeContent
) : NudgeItem {

    data class VoteNudgeContent(
        val voteOption: VoteOption
    ) {
    }

    override fun mustViewSequence(): Int {
        return mustViewSequence
    }

    override fun nudgeType(): NudgeType {
        return NudgeType.VOTE
    }

    override fun content(): Any {
        return content
    }

}
