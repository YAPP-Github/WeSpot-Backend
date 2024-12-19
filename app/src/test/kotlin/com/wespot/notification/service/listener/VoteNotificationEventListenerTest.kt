package com.wespot.notification.service.listener

import com.wespot.common.service.ServiceTest
import com.wespot.firebase.FirebaseNotificationService
import com.wespot.notification.NotificationType
import com.wespot.notification.port.out.NotificationPort
import com.wespot.user.event.SignUpUserEvent
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.VoteIdentifier
import com.wespot.vote.event.EndVoteEvent
import com.wespot.vote.event.ReceivedVoteEvent
import com.wespot.vote.event.RegisteredVoteEvent
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.voteoption.fixture.VoteOptionFixture
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.Test

class VoteNotificationEventListenerTest @Autowired constructor(
    private val voteNotificationEventListener: VoteNotificationEventListener,
    private val userPort: UserPort,
    private val voteOptionPort: VoteOptionPort,
    private val notificationPort: NotificationPort,
) : ServiceTest() {

    @Test
    fun `학급에 새로운 친구가 가입하면 알림이 발송된다`() {
        // given
        val user1 = userPort.save(UserFixture.createWithId(0))
        val user2 = userPort.save(UserFixture.createWithId(0))
        val sendService = mockk<FirebaseNotificationService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        voteNotificationEventListener.signUpNewUser(SignUpUserEvent(user2))
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 1
        notifications[0].userId shouldBe user1.id
        notifications[0].type shouldBe NotificationType.VOTE
        notifications[0].date shouldBe LocalDate.now()
    }

    @Test
    fun `새로운 인원이 투표에 참여한 경우 알림이 발송된다`() {
        // given
        val sendService = mockk<FirebaseNotificationService>()
        val users = (1..6).map { userPort.save(UserFixture.createWithIdAndEmail(0, "hello${it}@Kakao")) }
        val voteOptions = (1..5).map { voteOptionPort.save(VoteOptionFixture.create()) }
        val voteIdentifier = VoteIdentifier.of(users[0], LocalDate.now())
        val vote = Vote.of(voteIdentifier, voteOptions, null)
        (0..4).forEach { vote.addBallot(voteOptions[0].id, users[it], users[it + 1], LocalDateTime.now()) }

        // when
        every { sendService.sendMulticastNotification(any(), any()) } returns Unit
        voteNotificationEventListener.registerVote(RegisteredVoteEvent(0, users[0], vote))
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 10
        notifications[5].userId shouldBe users[1].id
        notifications[6].userId shouldBe users[2].id
        notifications[7].userId shouldBe users[3].id
        notifications[8].userId shouldBe users[4].id
        notifications[5].type shouldBe NotificationType.VOTE_RESULT
        notifications[5].targetId shouldBe 0
        notifications[5].date shouldBe LocalDate.now()
    }

    @Test
    fun `투표를 받은 이에게 알림이 발송된다`() {
        // given
        val receiver = userPort.save(UserFixture.createWithId(0))
        val sender = userPort.save(UserFixture.createWithIdAndEmail(0, "hello@Kakao"));
        val sendService = mockk<FirebaseNotificationService>()

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        voteNotificationEventListener.receiveVote(ReceivedVoteEvent(sender, receiver))
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 1
        notifications[0].userId shouldBe receiver.id
        notifications[0].type shouldBe NotificationType.VOTE_RECEIVED
        notifications[0].targetId shouldBe 0
        notifications[0].date shouldBe LocalDate.now()
    }

    @Test
    fun `투표가 종료되었을 때, 반 친구들에게 알림이 발송된다`() {
        // given
        val sendService = mockk<FirebaseNotificationService>()
        val users = (1..5).map { userPort.save(UserFixture.createWithIdAndEmail(0, "hello${it}@Kakao")) }

        // when
        every { sendService.sendNotification(any(), any()) } returns Unit
        voteNotificationEventListener.endVote(EndVoteEvent())
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 5
        notifications[0].userId shouldBe users[0].id
        notifications[1].userId shouldBe users[1].id
        notifications[2].userId shouldBe users[2].id
        notifications[3].userId shouldBe users[3].id
        notifications[4].userId shouldBe users[4].id
        notifications[0].type shouldBe NotificationType.VOTE_RESULT
        notifications[0].targetId shouldBe 0
        notifications[0].date shouldBe LocalDate.now().minusDays(1)
    }

}
