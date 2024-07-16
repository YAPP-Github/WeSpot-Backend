package com.wespot.school.port.out

import com.wespot.school.School

interface SchoolPort {

    fun save(school: School): School

    fun findById(id: Long): School?

}
