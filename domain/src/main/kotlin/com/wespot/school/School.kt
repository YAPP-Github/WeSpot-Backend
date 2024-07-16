package com.wespot.school

data class School(
    val id: Long,
    val name: String,
    val category: SchoolCategory,
    val schoolType: SchoolType,
    val estType: EstType,
    val region: String,
    val address: String,
) {
}
