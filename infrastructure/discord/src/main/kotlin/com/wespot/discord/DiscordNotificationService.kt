package com.wespot.discord

import com.wespot.common.out.ErrorNotificationPort
import org.springframework.stereotype.Component

@Component
class DiscordNotificationService : ErrorNotificationPort {

    override fun notifyWarning() {
        TODO("Not yet implemented")
    }

    override fun notifyError() {
        TODO("Not yet implemented")
    }

}
