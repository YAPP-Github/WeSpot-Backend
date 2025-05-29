package com.wespot.message.service.real

import com.wespot.common.service.ServiceTest
import com.wespot.message.MessageTimeValidator
import com.wespot.message.fixture.MessageFixture
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.ModifyMessageService
import com.wespot.notification.port.out.NotificationPort
import com.wespot.school.SchoolJpaRepository
import com.wespot.school.fixture.SchoolFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.UserPort
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class RealModifyMessageServiceTest @Autowired constructor(
    private val modifyMessageService: ModifyMessageService,
    private val userPort: UserPort,
    private val messagePort: MessagePort,
    private val notificationPort: NotificationPort,
    private val schoolJpaRepository: SchoolJpaRepository,
) : ServiceTest() {

    @Test
    fun `쪽지를 처음 읽었을 때, 송신자에게 알림이 발송된다`() {
        // given
        val fixedClock = Clock.fixed(Instant.parse("2023-03-18T18:00:00Z"), ZoneId.of("UTC"))
        MessageTimeValidator.setClock(fixedClock)

        val school = schoolJpaRepository.save(SchoolFixture.generateJpaEntity())
        val receiver = userPort.save(UserFixture.createWithIdAndEmail(0, "Test1@KAKAO", school = school))
        val sender = userPort.save(UserFixture.createWithIdAndEmail(0, "Test2@KAKAO", school = school))

        UserFixture.setSecurityContextUser(receiver)
        val message = messagePort.save(
            MessageFixture.createMessageWithReceived(
                content = "content",
                receiverId = receiver.id,
                senderId = sender.id,
                senderName = "senderName",
            )
        )

        // when
        modifyMessageService.readMessage(messageId = message.id)
        modifyMessageService.readMessage(messageId = message.id)
        val notifications = notificationPort.findAll()

        // then
        notifications.size shouldBe 1
        notifications[0].targetId shouldBe message.id
        notifications[0].userId shouldBe sender.id
        notifications[0].title shouldBe "방금 ${receiver.name}님이 내가 보낸 쪽지를 읽었어요 \uD83E\uDEE2"
        notifications[0].body shouldBe "앞으로도 에버가 큐피드가 되어 드릴게요"

        clearAllMocks()
        MessageTimeValidator.resetClock()
    }

}
