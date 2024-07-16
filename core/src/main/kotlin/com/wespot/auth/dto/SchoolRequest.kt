package com.wespot.auth.dto

import com.wespot.school.SchoolType

data class SchoolRequest(
    val name: String,
    val schoolType: SchoolType,
    val region: String,
    val address: String,
)
