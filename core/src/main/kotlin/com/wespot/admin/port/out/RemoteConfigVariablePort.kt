package com.wespot.admin.port.out

import com.wespot.admin.RemoteConfig

interface RemoteConfigVariablePort {

    fun getRemoteConfigVariables(): List<RemoteConfig>

    fun setRemoteConfigVariables(key: String, value: String): Unit

    fun deleteRemoteConfigVariables(key: String)

}
