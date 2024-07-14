package com.wespot.user

object SettingMapper {

    fun mapToDomainEntity(settingJpaEntity: SettingJpaEntity): Setting =
        Setting(
            id = settingJpaEntity.id,
            userId = settingJpaEntity.userId,
            isEnableNotification = settingJpaEntity.isEnableNotification
        )

    fun mapToJpaEntity(setting: Setting): SettingJpaEntity =
        SettingJpaEntity(
            id = setting.id,
            userId = setting.userId,
            isEnableNotification = setting.isEnableNotification
        )

}