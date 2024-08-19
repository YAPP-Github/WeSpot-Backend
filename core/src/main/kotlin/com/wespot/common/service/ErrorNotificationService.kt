package com.wespot.common.service

import com.wespot.common.`in`.ErrorNotificationUseCase
import com.wespot.common.out.ErrorNotificationPort
import org.springframework.stereotype.Service

@Service
class ErrorNotificationService(
    private val errorNotificationPort: ErrorNotificationPort
) : ErrorNotificationUseCase {

    override fun notifyError(isError: Boolean, exceptionMessage: String) {
        if (isError) {
            errorNotificationPort.notifyError(exceptionMessage)
            return
        }
        errorNotificationPort.notifyWarning(exceptionMessage)
    }

}
