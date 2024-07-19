package com.wespot.user.port.out

import com.wespot.user.ProfileBackground

interface ProfileBackgroundPort {

    fun findAll(): List<ProfileBackground>

}
