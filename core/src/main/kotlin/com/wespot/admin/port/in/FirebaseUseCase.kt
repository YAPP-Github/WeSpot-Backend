package com.wespot.admin.port.`in`

import com.wespot.admin.dto.ModifiedRemoteConfigVariableRequest
import com.wespot.admin.dto.RemoteConfigVariableResponse
import com.wespot.admin.dto.SavedRemoteConfigVariableRequest

interface FirebaseUseCase {

    fun findAllVariableInRemoteConfig(): List<RemoteConfigVariableResponse>

    fun modifyRemoteConfigVariables(request: ModifiedRemoteConfigVariableRequest)

    fun addRemoteConfigVariables(request: SavedRemoteConfigVariableRequest)

    fun remoteRemoteConfigVariables(key: String)

}
