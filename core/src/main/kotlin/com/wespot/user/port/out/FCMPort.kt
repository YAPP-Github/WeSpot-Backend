package com.wespot.user.port.out

import com.wespot.user.FCM

interface FCMPort {

    fun save(fcm: FCM): FCM

}
