package com.wespot.common.out

interface ErrorNotificationPort {

    fun notifyWarning(message: String)

    fun notifyError(message: String)

}
