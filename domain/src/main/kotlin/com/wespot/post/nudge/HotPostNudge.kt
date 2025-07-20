package com.wespot.post.nudge

import com.wespot.post.Post
import com.wespot.post.nudge.vo.NudgeType

data class HotPostNudge(
    val mustViewSequence: Int,
    val content: HotPostNudgeContent
) : NudgeItem {

    companion object {
        fun of(mustViewSequence: Int, top5Post: List<Post>): HotPostNudge {
            return HotPostNudge(mustViewSequence = mustViewSequence, content = HotPostNudgeContent(top5Post))
        }
    }

    data class HotPostNudgeContent(
        val posts: List<Post>
    ) {
    }

    override fun mustViewSequence(): Int {
        return mustViewSequence
    }

    override fun nudgeType(): NudgeType {
        return NudgeType.HOT_POST
    }

    override fun content(): Any {
        return content
    }

}
