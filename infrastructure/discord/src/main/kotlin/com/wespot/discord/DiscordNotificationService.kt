package com.wespot.discord

import com.wespot.common.out.ErrorNotificationPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DiscordNotificationService(
    private val discordNotificationClient: DiscordNotificationClient,
    @Value("\${discord.warning.webhook-url}")
    private val warningDiscordWebHookUrl: String,
    @Value("\${discord.error.webhook-url}")
    private val errorDiscordWebHookUrl: String,
) : ErrorNotificationPort {

    override fun notifyWarning(message: String) {
        val discordMessage = DiscordMessage.createWarningDiscordMessage(message)
        println(warningDiscordWebHookUrl)
        discordNotificationClient.notifyWarning(warningDiscordWebHookUrl, discordMessage)
    }

    override fun notifyError(message: String) {
        val discordMessage = DiscordMessage.createErrorDiscordMessage(message)
        println(errorDiscordWebHookUrl)
        discordNotificationClient.notifyWarning(errorDiscordWebHookUrl, discordMessage)
    }

}
