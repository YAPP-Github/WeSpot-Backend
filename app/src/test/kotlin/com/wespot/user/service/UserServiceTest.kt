package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.school.fixture.SchoolFixture
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.UserIntroduction
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.fixture.ProfileFixture
import com.wespot.user.fixture.UserFixture
import com.wespot.user.port.out.ProfileBackgroundPort
import com.wespot.user.port.out.ProfileIconPort
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserServiceTest : BehaviorSpec({
    val userPort = mockk<UserPort>()
    val schoolPort = mockk<SchoolPort>()
    val profilePort = mockk<ProfilePort>()
    val profileBackgroundPort = mockk<ProfileBackgroundPort>()
    val profileIconPort = mockk<ProfileIconPort>()
    val userService = UserService(
        userPort = userPort,
        schoolPort = schoolPort,
        profilePort = profilePort,
        profileBackgroundPort = profileBackgroundPort,
        profileIconPort = profileIconPort
    )

    given("UserService 테스트") {
        val school = SchoolFixture.generate(id = 1)
        val user = UserFixture.createWithIdSchool(1, school = school)
        val profile = ProfileFixture.createWithId(1)

        every { userPort.findById(user.id) } returns user
        every { userPort.save(any()) } returns user
        every { userPort.findByEmail(any()) } returns user
        every { schoolPort.findById(school.id) } returns school

        UserFixture.setSecurityContextUser(user)

        `when`("me() 메서드를 호출할 때") {
            every { getLoginUser(userPort) } returns user
            every { schoolPort.findById(user.school.id) } returns school

            val userResponse = userService.me()

            then("올바른 UserResponse 객체를 반환해야 한다") {
                userResponse shouldBe UserResponse.from(user, school.name)
            }

            then("학교 정보를 가져와야 한다") {
                userResponse.schoolName shouldBe school.name
            }
        }

        `when`("updateProfile() 메서드를 호출할 때") {
            val profileRequest = UserFixture.updateProfileRequest()

            every { getLoginUser(userPort) } returns user
            every { profilePort.save(any()) } returns profile
            every { userPort.save(any()) } returns user

            userService.updateProfile(profileRequest)

            then("프로필이 업데이트 되어야 한다") {
                user.introduction.introduction shouldBe profileRequest.introduction
                user.profile.backgroundColor shouldBe profileRequest.backgroundColor
                user.profile.iconUrl shouldBe profileRequest.iconUrl
            }
        }

    }
})
