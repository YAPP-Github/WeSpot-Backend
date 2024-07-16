package com.wespot.school


object SchoolMapper {

    fun mapToDomainEntity(school: SchoolJpaEntity): School =
        School(
            id = school.id,
            name = school.name,
            schoolType = school.schoolType,
            region = school.region,
            address = school.address,
        )

    fun mapToJpaEntity(school: School): SchoolJpaEntity =
        SchoolJpaEntity(
            id = school.id,
            name = school.name,
            schoolType = school.schoolType,
            region = school.region,
            address = school.address,
        )
}