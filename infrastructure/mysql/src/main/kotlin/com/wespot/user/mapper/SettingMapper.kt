package com.wespot.user.mapper

import com.wespot.user.Setting
import com.wespot.user.entity.SettingJpaEntity

object SettingMapper {

    fun mapToDomainEntity(settingJpaEntity: SettingJpaEntity): Setting =
        Setting(
            isEnableVoteNotification = settingJpaEntity.isEnableVoteNotification,
            isEnableMessageNotification = settingJpaEntity.isEnableMessageNotification,
            isEnableEventNotification = settingJpaEntity.isEnableEventNotification
        )

    fun mapToJpaEntity(setting: Setting): SettingJpaEntity =
        SettingJpaEntity(
            isEnableVoteNotification = setting.isEnableVoteNotification,
            isEnableMessageNotification = setting.isEnableMessageNotification,
            isEnableEventNotification = setting.isEnableEventNotification
        )

}
