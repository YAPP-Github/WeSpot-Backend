package com.wespot.user.dto.response

import com.wespot.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val gender : String,
    val introduction : String,
    val schoolName : String,
    val grade : Int,
    val groupNumber : Int,
    val profile: ProfileResponse?
) {

    companion object {

        fun from(user: User, school: String): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                gender = user.name,
                introduction = user.introduction,
                schoolName = school,
                grade = user.grade,
                groupNumber = user.classNumber,
                profile = user.profile.let { ProfileResponse.from(it) }
            )
        }

    }

}
