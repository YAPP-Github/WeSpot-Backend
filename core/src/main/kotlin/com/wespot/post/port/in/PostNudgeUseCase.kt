package com.wespot.post.port.`in`

import com.wespot.post.nudge.NudgeItem

interface PostNudgeUseCase {

    fun findAllNudgeModalsBySequence(startSequence: Int, endSequence: Int): List<NudgeItem>

}
