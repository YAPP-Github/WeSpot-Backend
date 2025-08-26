package com.wespot.user.mapper

import com.wespot.user.Setting
import com.wespot.user.entity.SettingJpaEntity

object SettingMapper {

    fun mapToDomainEntity(settingJpaEntity: SettingJpaEntity): Setting =
        Setting(
            isEnableVoteNotification = settingJpaEntity.isEnableVoteNotification,
            isEnableMessageNotification = settingJpaEntity.isEnableMessageNotification,
            isEnableMarketingNotification = settingJpaEntity.isEnableMarketingNotification,
            isEnableMessage = settingJpaEntity.isEnableMessageV2,
            isEnablePostNotification = settingJpaEntity.isEnablePostNotification,
        )

    fun mapToJpaEntity(setting: Setting): SettingJpaEntity =
        SettingJpaEntity(
            isEnableVoteNotification = setting.isEnableVoteNotification,
            isEnableMessageNotification = setting.isEnableMessageNotification,
            isEnableMarketingNotification = setting.isEnableMarketingNotification,
            isEnableMessageV2 = setting.isEnableMessage,
            isEnablePostNotification = setting.isEnablePostNotification,
        )

}
