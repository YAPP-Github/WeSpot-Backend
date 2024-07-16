package com.wespot.user.port.out

import com.wespot.user.Profile

interface ProfilePort {

    fun save(profile: Profile): Profile
}