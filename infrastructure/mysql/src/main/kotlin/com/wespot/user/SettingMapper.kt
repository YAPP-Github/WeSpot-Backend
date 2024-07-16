package com.wespot.user

object SettingMapper {

    fun mapToDomainEntity(settingJpaEntity: SettingJpaEntity): Setting =
        Setting(
            id = settingJpaEntity.id,
            isEnableNotification = settingJpaEntity.isEnableNotification
        )

    fun mapToJpaEntity(setting: Setting): SettingJpaEntity =
        SettingJpaEntity(
            id = setting.id,
            isEnableNotification = setting.isEnableNotification
        )

}
