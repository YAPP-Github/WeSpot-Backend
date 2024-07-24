package com.wespot.school.fixture

import com.wespot.school.School
import com.wespot.school.SchoolType

object SchoolFixture {

    fun createWithId(id: Long): School {
        return School(
            id = id,
            name = "Test School",
            schoolType = SchoolType.HIGH,
            region = "Test Region",
            address = "Test Address"
        )
    }

    fun createSchool(
        id: Long,
        name: String,
        schoolType: SchoolType,
        region: String,
        address: String
    ): School {
        return School(
            id = id,
            name = name,
            schoolType = schoolType,
            region = region,
            address = address
        )
    }

}
