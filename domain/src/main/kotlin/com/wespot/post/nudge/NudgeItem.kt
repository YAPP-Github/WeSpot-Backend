package com.wespot.post.nudge

import com.wespot.post.nudge.vo.NudgeType

interface NudgeItem {

    fun mustViewSequence(): Int

    fun nudgeType(): NudgeType

    fun content(): Any

}
