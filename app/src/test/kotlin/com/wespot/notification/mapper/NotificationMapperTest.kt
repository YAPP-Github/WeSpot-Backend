package com.wespot.notification.mapper

import com.wespot.notification.NotificationMapper
import com.wespot.notification.NotificationType
import com.wespot.notification.fixtrue.NotificationFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class NotificationMapperTest : BehaviorSpec({

    given("Domain Entity 알림을") {
        val domainEntity = NotificationFixture.createWithType(NotificationType.MESSAGE)
        `when`("Jpa Entity 알림으로") {
            val jpaEntity = NotificationMapper.mapToJpaEntity(domainEntity)
            then("변환한다.") {
                jpaEntity.id shouldBe domainEntity.id
                jpaEntity.userId shouldBe domainEntity.userId
                jpaEntity.type shouldBe domainEntity.type
                jpaEntity.date shouldBe domainEntity.date
                jpaEntity.targetId shouldBe domainEntity.targetId
                jpaEntity.title shouldBe domainEntity.title
                jpaEntity.body shouldBe domainEntity.body
                jpaEntity.isRead shouldBe domainEntity.isRead
                jpaEntity.readAt shouldBe domainEntity.readAt
                jpaEntity.isEnabled shouldBe domainEntity.isEnabled
                jpaEntity.baseEntity.createdAt shouldBe domainEntity.createdAt
                jpaEntity.baseEntity.updatedAt shouldBe domainEntity.updatedAt
            }
        }
    }

    given("Jpa Entity 알림을") {
        val jpaEntity =
            NotificationMapper.mapToJpaEntity(NotificationFixture.createWithType(NotificationType.MESSAGE))
        `when`("Domain Entity 알림으로") {
            val domainEntity = NotificationMapper.mapToDomainEntity(jpaEntity)
            then("변환한다.") {
                domainEntity.id shouldBe jpaEntity.id
                domainEntity.userId shouldBe jpaEntity.userId
                domainEntity.type shouldBe jpaEntity.type
                domainEntity.date shouldBe jpaEntity.date
                domainEntity.targetId shouldBe jpaEntity.targetId
                domainEntity.title shouldBe jpaEntity.title
                domainEntity.body shouldBe jpaEntity.body
                domainEntity.isRead shouldBe jpaEntity.isRead
                domainEntity.readAt shouldBe jpaEntity.readAt
                domainEntity.isEnabled shouldBe jpaEntity.isEnabled
                domainEntity.createdAt shouldBe jpaEntity.baseEntity.createdAt
                domainEntity.updatedAt shouldBe jpaEntity.baseEntity.updatedAt
            }
        }
    }

})
