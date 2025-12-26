package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.school.SchoolMapper
import com.wespot.school.SchoolType
import com.wespot.school.fixture.SchoolFixture
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class SearchServiceTest : BehaviorSpec({
    val userPort = mockk<UserPort>()
    val schoolPort = mockk<SchoolPort>()
    val blockUserPort = mockk<BlockedUserPort>()
    val searchUserService = SearchUserService(
        userPort = userPort,
        schoolPort = schoolPort,
        blockedUserPort = blockUserPort
    )

    given("cursorId가 0일 때") {
        `when`("첫 페이지를 조회하면") {
            val keyword = "김"
            val cursorId = 0L
            val pageable = PageRequest.of(0, 10, Sort.by("id"))

            val users = listOf(
                UserFixture.createUser(1L, "user1@example.com", "김갑수", SchoolFixture.generateJpaEntity(id = 1L)),
                UserFixture.createUser(2L, "user2@example.com", "김경식", SchoolFixture.generateJpaEntity(id = 2L)),
                UserFixture.createUser(3L, "user3@example.com", "김경수", SchoolFixture.generateJpaEntity(id = 3L))
            )
            val school1 = SchoolFixture.createSchool(1L, "서울고등학교", SchoolType.HIGH, "서울", "서울시 강남구")
            val school2 = SchoolFixture.createSchool(2L, "부산고등학교", SchoolType.HIGH, "부산", "부산시 남구")
            val school3 = SchoolFixture.createSchool(3L, "광주고등학교", SchoolType.HIGH, "광주", "광주시 서구")
            val loginUser = UserFixture.createWithIdSchool(1L)

            mockkStatic(SecurityUtils::class)
            every { SecurityUtils.getLoginUser(userPort) } returns loginUser
            every {
                userPort.searchUsers(
                    keyword,
                    null,
                    null,
                    null,
                    null,
                    pageable,
                    loginUser.id,
                    emptyList()
                )
            } returns users
            every {
                userPort.countUsersAfterCursor(
                    keyword,
                    null,
                    null,
                    null,
                    null,
                    loginUser.id,
                    emptyList()
                )
            } returns 0L
            every { schoolPort.findById(1L) } returns school1
            every { schoolPort.findById(2L) } returns school2
            every { schoolPort.findById(3L) } returns school3

            val result = searchUserService.searchUsers(keyword, cursorId)

            then("첫 페이지의 사용자들을 반환한다") {
                verify { userPort.searchUsers(keyword, null, null, null, null, pageable, loginUser.id, emptyList()) }
                result.users.size shouldBe 3
                result.hasNext shouldBe false
            }

            unmockkStatic(SecurityUtils::class)
        }
    }

    given("cursorId가 0이 아닐 때") {
        `when`("커서 기반으로 사용자를 조회하면") {
            val keyword = "경"
            val cursorId = 1L
            val pageable = PageRequest.of(0, 10, Sort.by("id"))
            val school = SchoolFixture.generate(1L, "서울고등학교", SchoolType.HIGH, "서울", "서울시 강남구")
            val cursorUser =
                UserFixture.createUser(1L, "cursor@example.com", "김갑수", school = SchoolMapper.mapToJpaEntity(school))
            val users = listOf(
                UserFixture.createUser(2L, "user2@example.com", "김경식", SchoolFixture.generateJpaEntity(2L)),
                UserFixture.createUser(3L, "user3@example.com", "김경수", SchoolFixture.generateJpaEntity(3L))
            )
            val loginUser = UserFixture.createWithIdSchool(1L)

            mockkStatic(SecurityUtils::class)
            every { SecurityUtils.getLoginUser(userPort) } returns loginUser
            every { userPort.findById(cursorId) } returns cursorUser
            every { userPort.countUsersAfterCursor("경", "김갑수", "서울고등학교", 2, 1, loginUser.id, emptyList()) } returns 0L
            every { schoolPort.findById(1L) } returns school
            every {
                userPort.searchUsers(
                    keyword,
                    "김갑수",
                    "서울고등학교",
                    2,
                    cursorId,
                    pageable,
                    loginUser.id,
                    emptyList()
                )
            } returns users

            val result = searchUserService.searchUsers(keyword, cursorId)

            then("커서 기반의 사용자들을 반환한다") {
                verify { userPort.findById(cursorId) }
                verify {
                    userPort.searchUsers(
                        keyword,
                        "김갑수",
                        "서울고등학교",
                        2,
                        cursorId,
                        pageable,
                        loginUser.id,
                        emptyList()
                    )
                }
                result.users.size shouldBe 2
                result.hasNext shouldBe false
            }

            unmockkStatic(SecurityUtils::class)
        }
    }

    given("다음 페이지가 있을 때") {
        `when`("다음 페이징을 처리하면") {
            val keyword = "경"
            val cursorId = 2L
            val pageable = PageRequest.of(0, 10, Sort.by("id"))
            val school = SchoolFixture.generate(2L, "부산고등학교", SchoolType.HIGH, "부산", "부산시 남구")
            val cursorUser =
                UserFixture.createUser(2L, "cursor@example.com", "김경식", school = SchoolMapper.mapToJpaEntity(school))
            val users = listOf(
                UserFixture.createUser(3L, "user3@example.com", "김경수", SchoolFixture.generateJpaEntity(id = 3L))
            )
            val loginUser = UserFixture.createWithIdSchool(1L)

            mockkStatic(SecurityUtils::class)
            every { SecurityUtils.getLoginUser(userPort) } returns loginUser
            every { userPort.countUsersAfterCursor("경", "김경식", "부산고등학교", 2, 2, loginUser.id, emptyList()) } returns 0L
            every { userPort.findById(cursorId) } returns cursorUser
            every { schoolPort.findById(2L) } returns school
            every {
                userPort.searchUsers(
                    keyword,
                    "김경식",
                    "부산고등학교",
                    2,
                    cursorId,
                    pageable,
                    loginUser.id,
                    emptyList()
                )
            } returns users

            val result = searchUserService.searchUsers(keyword, cursorId)

            then("다음 페이지의 사용자들을 반환한다") {
                verify { userPort.findById(cursorId) }
                verify {
                    userPort.searchUsers(
                        keyword,
                        "김경식",
                        "부산고등학교",
                        2,
                        cursorId,
                        pageable,
                        loginUser.id,
                        emptyList()
                    )
                }
                result.users.size shouldBe 1
                result.hasNext shouldBe false
            }

            unmockkStatic(SecurityUtils::class)
        }
    }

    given("정렬 조건이 있을 때") {
        `when`("이름순, 학교순, 학교타입순으로 정렬하면") {
            val keyword = "김"
            val cursorId = 0L
            val pageable = PageRequest.of(0, 10, Sort.by("id"))
            val school1 = SchoolFixture.generate(1L, "서울고등학교", SchoolType.HIGH, "서울", "서울시 강남구")
            val school2 = SchoolFixture.generate(2L, "부산고등학교", SchoolType.HIGH, "부산", "부산시 남구")
            val school3 = SchoolFixture.generate(3L, "광주고등학교", SchoolType.HIGH, "광주", "광주시 서구")
            val users = listOf(
                UserFixture.createUser(1L, "user1@example.com", "김경식", SchoolMapper.mapToJpaEntity(school1)),
                UserFixture.createUser(2L, "user2@example.com", "김경수", SchoolMapper.mapToJpaEntity(school2)),
                UserFixture.createUser(3L, "user3@example.com", "김갑수", SchoolMapper.mapToJpaEntity(school3))
            )
            val loginUser = UserFixture.createWithIdSchool(1L)

            mockkStatic(SecurityUtils::class)
            every { SecurityUtils.getLoginUser(userPort) } returns loginUser
            every {
                userPort.searchUsers(
                    keyword,
                    null,
                    null,
                    null,
                    null,
                    pageable,
                    loginUser.id,
                    emptyList()
                )
            } returns users
            every { schoolPort.findById(1L) } returns school1
            every { schoolPort.findById(2L) } returns school2
            every { schoolPort.findById(3L) } returns school3

            val result = searchUserService.searchUsers(keyword, cursorId)

            then("정렬된 사용자들을 반환한다") {
                verify { userPort.searchUsers(keyword, null, null, null, null, pageable, loginUser.id, emptyList()) }
                result.users.size shouldBe 3
                result.hasNext shouldBe false

                result.users[0].name shouldBe "김경식"
                result.users[1].name shouldBe "김경수"
                result.users[2].name shouldBe "김갑수"
            }

            unmockkStatic(SecurityUtils::class)
        }
    }
})
