package com.wespot.report.service

import com.wespot.DatabaseCleanup
import com.wespot.message.MessageJpaRepository
import com.wespot.message.MessageMapper
import com.wespot.message.MessagePersistenceAdapter
import com.wespot.message.fixture.MessageFixture
import com.wespot.report.ReportJpaRepository
import com.wespot.report.ReportType
import com.wespot.report.dto.ReportRequest
import com.wespot.user.RestrictionType
import com.wespot.user.fixture.UserFixture
import com.wespot.user.mapper.UserMapper
import com.wespot.user.repository.UserJpaRepository
import com.wespot.vote.VoteJpaRepository
import com.wespot.vote.fixture.VoteJpaEntityFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SavedReportServiceTest @Autowired constructor(
    private val savedReportService: SavedReportService,
    private val userJpaRepository: UserJpaRepository,
    private val voteJpaRepository: VoteJpaRepository,
    private val messageJpaRepository: MessageJpaRepository,
    private val databaseCleanup: DatabaseCleanup,
    private val reportJpaRepository: ReportJpaRepository
) {

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    @Test
    fun `로그인하지 않은 유저가 신고하는 경우, 예외가 발생한다`() {
        // given
        val loginUser = UserFixture.createWithIdAndEmail(0, "TestEmail0@Kakao")
        UserFixture.setSecurityContextUser(loginUser)
        val savedMessage = messageJpaRepository.save(MessageMapper.mapToJpaEntity(MessageFixture.createWithId(0L)))
        val savedUser =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail1@Kakao")))
        val reportRequest = ReportRequest(
            targetId = savedMessage.id,
            targetUserId = savedUser.id,
            reportType = ReportType.MESSAGE
        )

        // when
        val shouldThrow = shouldThrow<NoSuchElementException> { savedReportService.reportReceived(reportRequest) }

        // then
        shouldThrow shouldHaveMessage "해당 계정이 존재하지 않습니다."
    }

    @Test
    fun `신고 당한 사람이 존재하지 않을 경우, 예외가 발생한다`() {
        // given
        val userJpaEntity =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail0@Kakao")))
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(userJpaEntity))
        messageJpaRepository.save(MessageMapper.mapToJpaEntity(MessageFixture.createWithId(0L)))
        val reportRequest = ReportRequest(
            targetId = 1L,
            targetUserId = 100L,
            reportType = ReportType.MESSAGE
        )

        // when
        val shouldThrow = shouldThrow<NoSuchElementException> { savedReportService.reportReceived(reportRequest) }

        // then
        shouldThrow shouldHaveMessage "유저를 찾을 수 없습니다."
    }

    @Test
    fun `신고하고자 하는 쪽지가 존재하지 않는 경우, 예외가 발생한다`() {
        // given
        val sender =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail0@Kakao")))
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(sender))
        val receiver =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail1@Kakao")))
        val reportRequest = ReportRequest(
            targetId = 1L,
            targetUserId = receiver.id,
            reportType = ReportType.MESSAGE
        )

        // when
        val shouldThrow = shouldThrow<IllegalArgumentException> { savedReportService.reportReceived(reportRequest) }

        // then
        shouldThrow shouldHaveMessage "존재하지 않는 쪽지에 신고를 할 수 없습니다."
    }

    @Test
    fun `쪽지의 송신자, 수진자에 맞춰 신고하지 않은 경우, 예외가 발생한다`() {
        // given
        val reportSender =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail0@Kakao")))
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(reportSender))
        val messageSender =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail2@Kakao")))
        val savedMessage = messageJpaRepository.save(
            MessageMapper.mapToJpaEntity(
                MessageFixture.createWithIdAndSenderIdAndReceiverId(
                    0L,
                    messageSender.id,
                    reportSender.id
                )
            )
        )
        val receiver =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail1@Kakao")))
        val reportRequest = ReportRequest(
            targetId = savedMessage.id,
            targetUserId = receiver.id,
            reportType = ReportType.MESSAGE
        )

        // when
        val shouldThrow = shouldThrow<IllegalArgumentException> { savedReportService.reportReceived(reportRequest) }

        // then
        shouldThrow shouldHaveMessage "존재하지 않는 쪽지에 신고를 할 수 없습니다."
    }

    @Test
    fun `제보시에, 같은 학급의 친구가 아닌 이를 제보할 경우, 예외가 발생한다`() {
        val reportSender =
            userJpaRepository.save(
                UserMapper.mapToJpaEntity(
                    UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber(
                        "TestEmail0@Kakao",
                        1,
                        1,
                        1
                    )
                )
            )
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(reportSender))
        val savedVote = voteJpaRepository.save(VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1))
        val reportReceiver =
            userJpaRepository.save(
                UserMapper.mapToJpaEntity(
                    UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber(
                        "TestEmail1@Kakao",
                        1,
                        1,
                        2
                    )
                )
            )
        val reportRequest = ReportRequest(
            targetId = savedVote.id,
            targetUserId = reportReceiver.id,
            reportType = ReportType.VOTE
        )

        // when
        val shouldThrow = shouldThrow<IllegalArgumentException> { savedReportService.reportReceived(reportRequest) }

        // then
        shouldThrow shouldHaveMessage "존재하지 않는 투표에 신고를 할 수 없습니다."
    }

    @Test
    fun `쪽지를 신고하는 경우, 쪽지가 지워진다`() {
        // given
        val sender =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail0@Kakao")))
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(sender))
        val receiver =
            userJpaRepository.save(UserMapper.mapToJpaEntity(UserFixture.createWithIdAndEmail(0, "TestEmail1@Kakao")))
        val savedMessage = messageJpaRepository.save(
            MessageMapper.mapToJpaEntity(
                MessageFixture.createWithIdAndSenderIdAndReceiverId(
                    0,
                    sender.id,
                    receiver.id
                )
            )
        )
        val reportRequest = ReportRequest(
            targetId = savedMessage.id,
            targetUserId = receiver.id,
            reportType = ReportType.MESSAGE
        )

        // when
        val savedReportResponse = savedReportService.reportReceived(reportRequest)
        val messages = messageJpaRepository.findAll()
        val existsById = reportJpaRepository.existsById(savedReportResponse.id)

        // then
        messages.size shouldBe 0
        existsById shouldBe true
    }

    @Test
    fun `한 사람의 반복된 제보로 인해 유저가 영구정지를 당한다`() {
        // given
        val sender =
            userJpaRepository.save(
                UserMapper.mapToJpaEntity(
                    UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber(
                        "TestEmail0@Kakao",
                        1,
                        1,
                        1
                    )
                )
            )
        UserFixture.setSecurityContextUser(UserMapper.mapToDomainEntity(sender))
        val savedVote = voteJpaRepository.save(VoteJpaEntityFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1))
        val reportReceiver =
            userJpaRepository.save(
                UserMapper.mapToJpaEntity(
                    UserFixture.createWithEmailAndSchoolIdAndGradeAndClassNumber(
                        "TestEmail1@Kakao",
                        1,
                        1,
                        1
                    )
                )
            )
        val reportRequest = ReportRequest(
            targetId = savedVote.id,
            targetUserId = reportReceiver.id,
            reportType = ReportType.VOTE
        )

        // when
        for (i in 1..15) {
            savedReportService.reportReceived(reportRequest)
        }
        val permanentUser = userJpaRepository.findById(reportReceiver.id).get()

        // then
        permanentUser.restrictionType shouldBe RestrictionType.VOTE_PERMANENT
    }

}
