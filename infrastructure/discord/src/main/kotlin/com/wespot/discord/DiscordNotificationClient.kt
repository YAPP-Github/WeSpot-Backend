package com.wespot.discord

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    name = "discord-client",
)
interface DiscordNotificationClient {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["{url}"],
        consumes = ["application/json"]
    )
    fun notifyWarning(url: String, @RequestBody message: DiscordMessage)

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["{url}"],
        consumes = ["application/json"]
    )
    fun notifyError(url: String, @RequestBody message: DiscordMessage)

}
