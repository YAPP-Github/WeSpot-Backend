package com.wespot.post.nudge

import com.wespot.post.nudge.vo.NudgeType

data class MessageNudge(
    val mustViewSequence: Int,
    val content: MessageNudgeContent
) : NudgeItem {

    data class MessageNudgeContent(
        val countRemainingMessages: Int = 0,
        val countUnReadMessages: Int = 0,
        val countUnReplayMessages: Int = 0,
    ) {
    }

    override fun mustViewSequence(): Int {
        return mustViewSequence
    }

    override fun nudgeType(): NudgeType {
        return NudgeType.MESSAGE
    }

    override fun content(): Any {
        return content
    }

}
