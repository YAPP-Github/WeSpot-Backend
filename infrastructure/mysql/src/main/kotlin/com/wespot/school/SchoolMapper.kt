package com.wespot.school

import com.wespot.user.mapper.UserMapper

object SchoolMapper {

    fun mapToDomainEntity(school: SchoolJpaEntity): School =
        School(
            id = school.id,
            user = UserMapper.mapToDomainEntity(school.user),
            name = school.name,
            schoolType = school.schoolType,
            region = school.region,
            address = school.address,
        )

    fun mapToJpaEntity(school: School): SchoolJpaEntity =
        SchoolJpaEntity(
            id = school.id,
            user = UserMapper.mapToJpaEntity(school.user),
            name = school.name,
            schoolType = school.schoolType,
            region = school.region,
            address = school.address,
        )
}