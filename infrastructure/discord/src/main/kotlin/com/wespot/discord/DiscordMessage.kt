package com.wespot.discord

data class DiscordMessage(
    val content: String,
    val embeds: List<Embed>
) {

    companion object {
        fun createWarningDiscordMessage(message: String): DiscordMessage {
            return DiscordMessage(
                "# Warning이 발생했어요.. 기천짱 칼 가져와",
                listOf(Embed.createWarningEmbed(message))
            )
        }

        fun createErrorDiscordMessage(message: String): DiscordMessage {
            return DiscordMessage(
                "# Error가 발생했어요!!!!!!!!!!!!!! 진호 엉덩이 가져와 매 맞게",
                listOf(Embed.createErrorEmbed(message))
            )
        }
    }
}
