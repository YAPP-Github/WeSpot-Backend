package com.wespot.discord

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "discord-warning-notification-client",
    url = "\${discord.warning.webhook-url}",
)
interface DiscordWarningNotificationClient {

    @PostMapping(consumes = ["application/json"])
    fun notifyWarning(@RequestBody message: DiscordMessage)

}
