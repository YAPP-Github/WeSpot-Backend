package com.wespot.common.service

import com.wespot.common.`in`.ErrorNotificationUseCase
import com.wespot.common.out.ErrorNotificationPort
import org.springframework.stereotype.Service

@Service
class ErrorNotificationService(
    private val errorNotificationPort: ErrorNotificationPort
) : ErrorNotificationUseCase {

    override fun notifyError(isError: Boolean, exception: Exception) {
        TODO("Not yet implemented")
    }

}
