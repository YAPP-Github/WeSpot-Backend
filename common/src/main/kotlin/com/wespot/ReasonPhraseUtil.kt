package com.wespot

import org.springframework.http.HttpStatus
import java.net.URI

object ReasonPhraseUtil {

    fun createErrorTypeInProblemDetail(httpStatus: HttpStatus): URI {
        return URI.create("/errors/${getReasonPhraseWithHyphen(httpStatus.reasonPhrase)}")
    }

    private fun getReasonPhraseWithHyphen(reasonPhrase: String): String {
        val hyphen = "-"
        return reasonPhrase.lowercase()
            .replace(" ", hyphen)
    }

}
