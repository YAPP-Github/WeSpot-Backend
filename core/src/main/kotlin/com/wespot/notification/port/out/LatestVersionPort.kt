package com.wespot.notification.port.out

import com.wespot.notification.LatestVersionType

interface LatestVersionPort {

    fun get(latestVersionType: LatestVersionType): String

}
