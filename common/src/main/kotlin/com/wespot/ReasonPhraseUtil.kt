package com.wespot

import org.springframework.http.HttpStatus
import java.net.URI

object ReasonPhraseUtil {

    fun createErrorTypeInProblemDetail(prefixUrl: String, httpStatus: HttpStatus): URI {
        return URI.create("${prefixUrl}/${getReasonPhraseWithHyphen(httpStatus.reasonPhrase)}")
    }

    private fun getReasonPhraseWithHyphen(reasonPhrase: String): String {
        val hyphen = "-"
        return reasonPhrase.lowercase()
            .replace(" ", hyphen)
    }

}
