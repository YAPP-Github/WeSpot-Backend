package com.wespot.firebase

import com.google.firebase.remoteconfig.*
import com.wespot.admin.RemoteConfig
import com.wespot.admin.port.out.RemoteConfigVariablePort
import org.springframework.stereotype.Component


@Component
class RemoteVariableHandler : RemoteConfigVariablePort {

    override fun getRemoteConfigVariables(): List<RemoteConfig> {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val firebaseLatestUpdateVersion = getLatestVersionByFirebaseRemoteConfig(firebaseRemoteConfig)

        return firebaseRemoteConfig.getTemplateAtVersion(firebaseLatestUpdateVersion)
            .parameters
            .entries
            .map { (key, value) -> RemoteConfig(key, (value.defaultValue as ParameterValue.Explicit).value) }
    }

    override fun setRemoteConfigVariables(key: String, value: String) {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val template = firebaseRemoteConfig
            .templateAsync
            .get()

        val parameterValue = Parameter()
            .setDefaultValue(ParameterValue.of(value))
            .setValueType(ParameterValueType.STRING)

        template.parameters[key] = parameterValue
        firebaseRemoteConfig.publishTemplate(template)
    }

    override fun deleteRemoteConfigVariables(key: String) {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val template = firebaseRemoteConfig
            .templateAsync
            .get()

        template.parameters
            .remove(key)
        firebaseRemoteConfig.publishTemplate(template)
    }

    private fun getLatestVersionByFirebaseRemoteConfig(firebaseRemoteConfig: FirebaseRemoteConfig): String? {
        val listVersionsOptions = ListVersionsOptions.builder()
            .setPageSize(1)
            .build()

        return firebaseRemoteConfig.listVersions(listVersionsOptions)
            .values
            .map { it.versionNumber }
            .first()
    }

}
