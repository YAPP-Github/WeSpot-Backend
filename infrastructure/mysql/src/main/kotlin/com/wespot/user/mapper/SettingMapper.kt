package com.wespot.user.mapper

import com.wespot.user.Setting
import com.wespot.user.entity.SettingJpaEntity

object SettingMapper {

    fun mapToDomainEntity(settingJpaEntity: SettingJpaEntity): Setting =
        Setting(
            isEnableVoteNotification = settingJpaEntity.isEnableVoteNotification,
            isEnableMessageNotification = settingJpaEntity.isEnableMessageNotification
        )

    fun mapToJpaEntity(setting: Setting): SettingJpaEntity =
        SettingJpaEntity(
            isEnableVoteNotification = setting.isEnableVoteNotification,
            isEnableMessageNotification = setting.isEnableMessageNotification
        )

}
