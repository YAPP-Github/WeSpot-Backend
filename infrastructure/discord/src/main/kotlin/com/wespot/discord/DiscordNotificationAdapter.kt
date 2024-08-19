package com.wespot.discord

import com.wespot.common.out.ErrorNotificationPort
import org.springframework.stereotype.Component

@Component
class DiscordNotificationAdapter(
    private val discordWarningNotificationClient: DiscordWarningNotificationClient,
    private val discordErrorNotificationClient: DiscordErrorNotificationClient
) : ErrorNotificationPort {

    override fun notifyWarning(message: String) {
        val discordMessage = DiscordMessage.createWarningDiscordMessage(message)
        discordWarningNotificationClient.notifyWarning(discordMessage)
    }

    override fun notifyError(message: String) {
        val discordMessage = DiscordMessage.createErrorDiscordMessage(message)
        discordErrorNotificationClient.notifyError(discordMessage)
    }

}
