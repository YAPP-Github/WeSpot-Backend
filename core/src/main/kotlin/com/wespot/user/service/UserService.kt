package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.school.School
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.User
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.*
import com.wespot.user.port.`in`.UserUseCase
import com.wespot.user.port.out.ProfileBackgroundPort
import com.wespot.user.port.out.ProfileIconPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
    private val schoolPort: SchoolPort,
    private val profileBackgroundPort: ProfileBackgroundPort,
    private val profileIconPort: ProfileIconPort
) : UserUseCase {

    override fun me(): UserResponse {
        val loginUser = getLoginUser(userPort = userPort)
        val school = (schoolPort.findById(loginUser.schoolId)
            ?: throw IllegalArgumentException("학교 정보를 찾을 수 없습니다."))

        return UserResponse.from(loginUser, school.name)
    }

    @Transactional
    override fun updateProfile(profile: UpdateProfileRequest): UserResponse {
        val loginUser = getLoginUser(userPort = userPort)
        val updateProfile = loginUser.updateProfile(
            introduction = profile.introduction,
            backgroundColor = profile.profile.backgroundColor,
            iconUrl = profile.profile.iconUrl
        )
        val saveUser = userPort.save(updateProfile)
        val school = findSchool(saveUser)

        return UserResponse.from(user = saveUser, school = school.name)
    }

    override fun backgrounds(): BackgroundListResponse {
        val backgroundResponses = profileBackgroundPort.findAll()
            .map { profileBackground -> BackgroundResponse.from(profileBackground)
        }

        return BackgroundListResponse.from(backgroundResponses)
    }

    override fun characters(): CharacterListResponse {
        val characterResponses = profileIconPort.findAll()
            .map { profileIcon -> CharacterResponse.from(profileIcon)
        }

        return CharacterListResponse.from(characterResponses)
    }

    private fun findSchool(user: User): School {
        return schoolPort.findById(user.schoolId)
                ?: throw IllegalArgumentException("학교 정보를 찾을 수 없습니다.")
    }
}
