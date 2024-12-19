package com.wespot.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ListVersionsOptions
import com.google.firebase.remoteconfig.ParameterValue
import com.wespot.notification.LatestVersionType
import com.wespot.notification.port.out.LatestVersionPort
import org.springframework.stereotype.Component

@Component
class LatestVersionGetter : LatestVersionPort {

    override fun get(latestVersionType: LatestVersionType): String {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val listVersionsOptions = ListVersionsOptions.builder()
            .setPageSize(1)
            .build()

        val firebaseLatestUpdateVersion = firebaseRemoteConfig.listVersions(listVersionsOptions)
            .values
            .map { it.versionNumber }
            .first()

        return extractVersion(firebaseRemoteConfig, firebaseLatestUpdateVersion, latestVersionType)
    }

    private fun extractVersion(
        firebaseRemoteConfig: FirebaseRemoteConfig,
        firebaseLatestUpdateVersion: String?,
        latestVersionType: LatestVersionType
    ): String {
        val latestVersion = firebaseRemoteConfig.getTemplateAtVersion(firebaseLatestUpdateVersion)
            .parameters[latestVersionType.variableName]

        if (latestVersion == null) {
            return LatestVersionType.MIN_VERSION.variableName
        }

        return (latestVersion.defaultValue as ParameterValue.Explicit).value
    }

}
