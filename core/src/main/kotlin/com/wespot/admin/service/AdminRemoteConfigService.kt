package com.wespot.admin.service

import com.wespot.admin.RemoteConfigs
import com.wespot.admin.dto.ModifiedRemoteConfigVariableRequest
import com.wespot.admin.dto.RemoteConfigVariableResponse
import com.wespot.admin.dto.SavedRemoteConfigVariableRequest
import com.wespot.admin.port.`in`.FirebaseUseCase
import com.wespot.admin.port.out.RemoteConfigVariablePort
import org.springframework.stereotype.Service

@Service
class AdminRemoteConfigService(
    private val remoteConfigVariablePort: RemoteConfigVariablePort
) : FirebaseUseCase {

    override fun findAllVariableInRemoteConfig(): List<RemoteConfigVariableResponse> {
        return remoteConfigVariablePort.getRemoteConfigVariables()
            .map { RemoteConfigVariableResponse.from(it) }
    }

    override fun modifyRemoteConfigVariables(request: ModifiedRemoteConfigVariableRequest) {
        val remoteConfigs = RemoteConfigs.from(remoteConfigVariablePort.getRemoteConfigVariables())
        remoteConfigs.modifyRemoteConfigVariable(request.key) {
            remoteConfigVariablePort.setRemoteConfigVariables(
                request.key,
                request.value
            )
        }
        remoteConfigVariablePort.setRemoteConfigVariables(request.key, request.value)
    }

    override fun addRemoteConfigVariables(request: SavedRemoteConfigVariableRequest) {
        val remoteConfigs = RemoteConfigs.from(remoteConfigVariablePort.getRemoteConfigVariables())

        remoteConfigs.addRemoteConfigVariable(request.key) {
            remoteConfigVariablePort.setRemoteConfigVariables(
                request.key,
                request.value
            )
        }
    }

    override fun remoteRemoteConfigVariables(key: String) {
        val remoteConfigs = RemoteConfigs.from(remoteConfigVariablePort.getRemoteConfigVariables())

        remoteConfigs.removeRemoteConfigVariable(key) {
            remoteConfigVariablePort.deleteRemoteConfigVariables(key)
        }
    }

}
