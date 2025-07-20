package com.wespot.post.nudge.vo

import kotlin.math.ceil

enum class NudgeType(
    val clazz: Class<out Any> = Any::class.java
) {

    VOTE(clazz = com.wespot.post.nudge.VoteNudge.VoteNudgeContent::class.java),
    MESSAGE(clazz = com.wespot.post.nudge.MessageNudge.MessageNudgeContent::class.java),
    HOT_POST(clazz = com.wespot.post.nudge.HotPostNudge.HotPostNudgeContent::class.java),
    ;

    companion object {
        private const val SPECIAL_VOTE_SEQUENCE = 3
        private const val HOT_POST_SEQUENCE = 15

        private const val COUNT_UNIT_OF_REPEATED_NUDGES = 45
        private val REPEATED_NUDGE_TYPES: List<NudgeType> = listOf(MESSAGE, VOTE)

        fun nudgeTypesByInquirySequences(startSequence: Int, endSequence: Int): List<NudgeTypeWithSequence> {
            val nudgeTypes = mutableListOf<NudgeTypeWithSequence>()

            if (SPECIAL_VOTE_SEQUENCE in startSequence..endSequence) {
                nudgeTypes.add(NudgeTypeWithSequence(sequence = SPECIAL_VOTE_SEQUENCE, nudgeType = VOTE))
            }
            if (HOT_POST_SEQUENCE in startSequence..endSequence) {
                nudgeTypes.add(NudgeTypeWithSequence(sequence = HOT_POST_SEQUENCE, nudgeType = HOT_POST))
            }

            var startRepeatedSequence =
                ceil(startSequence.toDouble() / COUNT_UNIT_OF_REPEATED_NUDGES.toDouble()) * COUNT_UNIT_OF_REPEATED_NUDGES
            while (startRepeatedSequence <= endSequence) {
                val index = (startRepeatedSequence / COUNT_UNIT_OF_REPEATED_NUDGES + 1) % REPEATED_NUDGE_TYPES.size
                nudgeTypes.add(
                    NudgeTypeWithSequence(
                        sequence = startRepeatedSequence.toInt(),
                        nudgeType = REPEATED_NUDGE_TYPES[index.toInt()]
                    )
                )
                startRepeatedSequence += COUNT_UNIT_OF_REPEATED_NUDGES
            }

            return nudgeTypes
        }

    }

}
