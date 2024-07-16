package com.wespot.school


data class School(
    val id: Long,
    val name: String,
    val schoolType: SchoolType,
    val region: String,
    val address: String,
) {

    companion object {
        fun create(
            name: String,
            schoolType: SchoolType,
            region: String,
            address: String
        ) =
            School(
                id = 0L,
                name = name,
                schoolType = schoolType,
                region = region,
                address = address
            )
    }
}
