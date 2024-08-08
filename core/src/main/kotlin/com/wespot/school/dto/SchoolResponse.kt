package com.wespot.school.dto

import com.wespot.school.School
import com.wespot.school.SchoolType

data class SchoolResponse(
    val id: Long,
    val name: String,
    val address: String,
    val type: SchoolType,
){
    companion object {

        fun from(
            school: School
        ): SchoolResponse {
            return SchoolResponse(
                id = school.id,
                name = school.name,
                address = school.address,
                type = school.schoolType
            )
        }

    }
}
