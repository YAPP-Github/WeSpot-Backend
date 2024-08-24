package com.wespot.auth.port.out

import com.wespot.auth.PersonalInfo

interface PersonalInfoPort {

    fun findByEmail(email: String): PersonalInfo?

    fun findBySocialId(socialId: String): PersonalInfo?

    fun save(personalInfo: PersonalInfo): PersonalInfo

}
