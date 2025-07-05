package com.wespot.common

object NotificationUtil {

    private const val SUMMARY_WORD = "..."

    fun summaryContent(content: String, maxLength: Int = 24): String {
        if (content.length > maxLength) {
            return content.substring(0, maxLength) + SUMMARY_WORD
        }

        return content
    }

}
