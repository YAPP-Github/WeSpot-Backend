package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.port.`in`.MessageV2UsingStatusUseCase
import com.wespot.post.nudge.HotPostNudge
import com.wespot.post.nudge.MessageNudge
import com.wespot.post.nudge.NudgeItem
import com.wespot.post.nudge.VoteNudge
import com.wespot.post.nudge.vo.NudgeType
import com.wespot.post.nudge.vo.NudgeTypeWithSequence
import com.wespot.post.port.`in`.HotPostInquiryUseCase
import com.wespot.post.port.`in`.PostNudgeModalUseCase
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.port.`in`.VoteOptionUsageUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostNudgeModalService(
    private val userPort: UserPort,
    private val messageV2UsingStatusUseCase: MessageV2UsingStatusUseCase,
    private val voteOptionUsageUseCase: VoteOptionUsageUseCase,
    private val hotPostInquiryUseCase: HotPostInquiryUseCase
) : PostNudgeModalUseCase {

    @Transactional(readOnly = true)
    override fun findAllNudgeModalsBySequence(startSequence: Int, endSequence: Int): List<NudgeItem> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val nudgeTypesByInquirySequences: List<NudgeTypeWithSequence> =
            NudgeType.nudgeTypesByInquirySequences(startSequence, endSequence)

        val nudgeTypes: List<NudgeType> = nudgeTypesByInquirySequences.map { it.nudgeType }
            .distinct()

        val messageNudgeContent: MessageNudge.MessageNudgeContent? = messageNudgeContent(loginUser, nudgeTypes)
        val voteNudgeContentIfMessageUnsatisfied: VoteNudge.VoteNudgeContent? =
            voteNudgeContentIfMessageUnsatisfied(loginUser, messageNudgeContent, nudgeTypes)
        val voteNudgeContent: VoteNudge.VoteNudgeContent? = voteNudgeContent(loginUser, nudgeTypes)
        val hotPostNudgeContent: HotPostNudge.HotPostNudgeContent? = hotPostNudgeContent(loginUser, nudgeTypes)

        return nudgeTypesByInquirySequences.map {
            when (it.nudgeType) {
                NudgeType.MESSAGE -> voteNudgeContentIfMessageUnsatisfied?.let { voteContent ->
                    VoteNudge(
                        mustViewSequence = it.sequence,
                        content = voteContent
                    )
                } ?: MessageNudge(mustViewSequence = it.sequence, content = messageNudgeContent!!)

                NudgeType.VOTE -> VoteNudge(mustViewSequence = it.sequence, content = voteNudgeContent!!)
                NudgeType.HOT_POST -> HotPostNudge(mustViewSequence = it.sequence, content = hotPostNudgeContent!!)
            }
        }
    }

    private fun messageNudgeContent(user: User, nudgeTypes: List<NudgeType>): MessageNudge.MessageNudgeContent? {
        if (!nudgeTypes.contains(NudgeType.MESSAGE)) {
            return null
        }

        val messageStatus = messageV2UsingStatusUseCase.getMessageStatus(user)

        return MessageNudge.MessageNudgeContent(
            countRemainingMessages = messageStatus.countRemainingMessages,
            countUnReadMessages = messageStatus.countUnReadMessages,
            countUnReplayMessages = messageStatus.countUnReplayMessages
        )
    }

    private fun voteNudgeContentIfMessageUnsatisfied(
        user: User,
        messageNudgeContent: MessageNudge.MessageNudgeContent?,
        nudgeTypes: List<NudgeType>
    ): VoteNudge.VoteNudgeContent? {
        if (!nudgeTypes.contains(NudgeType.MESSAGE) || messageNudgeContent == null) {
            return null
        }

        return VoteNudge.VoteNudgeContent(voteOptionUsageUseCase.findLeastFrequentlyUsedVoteOptionsAt(user, 2))
    }

    private fun voteNudgeContent(user: User, nudgeTypes: List<NudgeType>): VoteNudge.VoteNudgeContent? {
        if (!nudgeTypes.contains(NudgeType.VOTE)) {
            return null
        }

        return VoteNudge.VoteNudgeContent(voteOptionUsageUseCase.findLeastFrequentlyUsedVoteOptionsAt(user, 1))
    }

    private fun hotPostNudgeContent(user: User, nudgeTypes: List<NudgeType>): HotPostNudge.HotPostNudgeContent? {
        if (!nudgeTypes.contains(NudgeType.HOT_POST)) {
            return null
        }

        val countOfWantToView = 5
        return HotPostNudge.HotPostNudgeContent(hotPostInquiryUseCase.topPost(user, countOfWantToView))
    }

}
