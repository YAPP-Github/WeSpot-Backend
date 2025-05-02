package com.wespot.school.fixture

import com.wespot.common.util.RandomGenerator
import com.wespot.school.School
import com.wespot.school.SchoolJpaEntity
import com.wespot.school.SchoolType
import kotlinx.coroutines.flow.SharingCommand

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

    fun generate(
        id: Long = RandomGenerator.generateNonNullNumeric(2).toLong(),
        name: String = RandomGenerator.generateNonNullString(5),
        schoolType: SchoolType = RandomGenerator.generateNonNullEnum(SchoolType::class),
        region: String = RandomGenerator.generateNonNullString(5),
        address: String = RandomGenerator.generateNonNullString(5),
    ): School {
        return School(
            id = id,
            name = name,
            schoolType = schoolType,
            region = region,
            address = address
        )
    }

    fun generateJpaEntity(
        id: Long = RandomGenerator.generateNonNullNumeric(5).toLong(),
        name: String = RandomGenerator.generateNonNullString(5),
        schoolType: SchoolType = RandomGenerator.generateNonNullEnum(SchoolType::class),
        region: String = RandomGenerator.generateNonNullString(5),
        address: String = RandomGenerator.generateNonNullString(5),
    ): SchoolJpaEntity {
        return SchoolJpaEntity(
            id = id,
            name = name,
            schoolType = schoolType,
            region = region,
            address = address
        )
    }

}
