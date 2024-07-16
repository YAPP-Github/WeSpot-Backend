package com.wespot.school

import com.wespot.user.User

data class School(
    val id: Long,
    val user: User,
    val name: String,
    val schoolType: SchoolType,
    val region: String,
    val address: String,
) {

    companion object {
        fun create(
            user: User,
            name: String,
            schoolType: SchoolType,
            region: String,
            address: String
        ) =
            School(
                id = 0,
                user = user,
                name = name,
                schoolType = schoolType,
                region = region,
                address = address
            )
    }
}
