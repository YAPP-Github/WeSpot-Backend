package com.wespot.user

data class UserClass(
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int
) {

    companion object {

        fun of(user: User) = UserClass(
            schoolId = user.school.id,
            grade = user.grade,
            classNumber = user.classNumber
        )

    }

}
