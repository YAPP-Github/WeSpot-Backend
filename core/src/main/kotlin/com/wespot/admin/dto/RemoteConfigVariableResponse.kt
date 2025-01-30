package com.wespot.admin.dto

import com.wespot.admin.RemoteConfig

data class RemoteConfigVariableResponse(
    val key: String,
    val value: String
) {

    companion object {

        fun from(remoteConfig: RemoteConfig): RemoteConfigVariableResponse {
            return RemoteConfigVariableResponse(
                key = remoteConfig.key,
                value = remoteConfig.value
            )
        }

    }

}
