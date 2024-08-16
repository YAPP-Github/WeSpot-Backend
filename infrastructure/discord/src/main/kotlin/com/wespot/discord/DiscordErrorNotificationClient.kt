package com.wespot.discord

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "discord-error-notification-client",
    url = "\${discord.error.webhook-url}",
)
interface DiscordErrorNotificationClient {

    @PostMapping(consumes = ["application/json"])
    fun notifyError(@RequestBody message: DiscordMessage)

}
