package com.wespot.admin

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus

data class RemoteConfigs(
    val remoteConfigs: List<RemoteConfig>
) {

    companion object {

        fun from(remoteConfigs: List<RemoteConfig>): RemoteConfigs {
            return RemoteConfigs(remoteConfigs)
        }

    }

    fun modifyRemoteConfigVariable(key: String, savedFunction: () -> Unit) {
        if (!isExistsKey(key)) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "존재하지 않는 변수입니다.")
        }
        savedFunction()
    }

    private fun isExistsKey(key: String): Boolean {
        return remoteConfigs.any { it.key == key }
    }

    fun addRemoteConfigVariable(key: String, savedFunction: () -> Unit) {
        if (isExistsKey(key)) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "이미 존재하는 변수입니다.")
        }
        savedFunction()
    }

    fun removeRemoteConfigVariable(key: String, deleteFunction: () -> Unit) {
        if (!isExistsKey(key)) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "존재하지 않는 변수입니다.")
        }
        deleteFunction()
    }

}
