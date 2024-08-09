package com.wespot.common

object BadWordsChecker {

    private val replaceCharacters = listOf(
        " ",
        "@",
        "_",
        "-",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "(",
        ")",
        "!",
        ",",
        ".",
        "?",
        "/",
        "\\",
        "[",
        "]",
        "{",
        "}",
        "<",
        ">",
        "*",
        "&",
        "^",
        "%",
        "$",
        "#",
        "@",
        "!",
        "~",
        "`",
        "-",
        "_",
        "=",
        "+",
        "|",
        ";",
        ":",
        "'",
        "\""
    )

    fun checkProfanity(content: String): Boolean {
        val contentAfterRemoveProfanityMasking = removeProfanityMasking(content)
        println(contentAfterRemoveProfanityMasking)

        return BadWords.badWords
            .stream()
            .anyMatch(contentAfterRemoveProfanityMasking::contains)
    }

    private fun removeProfanityMasking(content: String): String {
        var replaceContent = content
        for (replaceCharacter in replaceCharacters) {
            replaceContent = replaceContent.replace(replaceCharacter, "")
        }
        return replaceContent
    }

}
