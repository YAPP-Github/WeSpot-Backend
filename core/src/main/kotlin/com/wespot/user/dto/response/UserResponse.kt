package com.wespot.user.dto.response

import com.wespot.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val gender : String,
    val introduction : String,
    val schoolName : String,
    val grade : Int,
    val classNumber : Int,
    val profile: ProfileResponse?,
    val needToAnnounceAboutPolicy: Boolean = false,
) {

    companion object {

        fun from(user: User, school: String): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                gender = user.gender.name,
                introduction = user.introduction.introduction,
                schoolName = school,
                grade = user.grade,
                classNumber = user.classNumber,
                profile = user.profile.let { ProfileResponse.from(it) },
                needToAnnounceAboutPolicy = user.needToAnnounceAboutPolicy(),
            )
        }

    }

}
