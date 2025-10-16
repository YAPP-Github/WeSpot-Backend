package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.PolicyType
import com.wespot.user.UserIntroduction
import com.wespot.user.UserPolicyAgreement
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.*
import com.wespot.user.port.`in`.UserUseCase
import com.wespot.user.port.out.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPort: UserPort,
    private val schoolPort: SchoolPort,
    private val profilePort: ProfilePort,
    private val profileBackgroundPort: ProfileBackgroundPort,
    private val profileIconPort: ProfileIconPort,
    private val userPolicyAgreementPort: UserPolicyAgreementPort,
) : UserUseCase {

    override fun me(): UserResponse {
        val loginUser = getLoginUser(userPort = userPort)
        val school = loginUser.school

        return UserResponse.from(loginUser, school.name)
    }

    @Transactional
    override fun updateProfile(profile: UpdateProfileRequest) {
        val loginUser = getLoginUser(userPort = userPort)
        val updatedIntroduction = profile.introduction ?: loginUser.introduction.introduction
        val updatedProfile = loginUser.profile.update(
            backgroundColor = profile.backgroundColor ?: loginUser.profile.backgroundColor,
            iconUrl = profile.iconUrl ?: loginUser.profile.iconUrl
        )

        val updatedUser = loginUser.copy(
            introduction = UserIntroduction.from(updatedIntroduction),
            profile = updatedProfile
        )

        profilePort.save(updatedProfile)
        userPort.save(updatedUser)
    }

    override fun backgrounds(): BackgroundListResponse {
        val backgroundResponses = profileBackgroundPort.findAll()
            .map { profileBackground ->
                BackgroundResponse.from(profileBackground)
            }

        return BackgroundListResponse.from(backgroundResponses)
    }

    override fun characters(): CharacterListResponse {
        val characterResponses = profileIconPort.findAll()
            .map { profileIcon ->
                CharacterResponse.from(profileIcon)
            }

        return CharacterListResponse.from(characterResponses)
    }

    @Transactional
    override fun allowNewPolicy(policyType: PolicyType): Long {
        val loginUser = getLoginUser(userPort = userPort)
        val userPolicyAgreement = UserPolicyAgreement(userId = loginUser.id, policyType = policyType)

        return userPolicyAgreementPort.save(userPolicyAgreement).id
    }

}
