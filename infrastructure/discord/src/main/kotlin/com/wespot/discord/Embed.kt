package com.wespot.discord

data class Embed(
    val title: String,
    val description: String,
) {

    companion object {

        fun createWarningEmbed(message: String): Embed {
            return Embed("# Warning 정보 \uD83D\uDEA8", message)
        }

        fun createErrorEmbed(message: String): Embed {
            return Embed("# Error 정보 \uD83D\uDEA8", message)
        }

    }
}
