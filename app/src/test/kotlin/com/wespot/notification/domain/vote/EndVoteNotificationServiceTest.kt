package com.wespot.notification.domain.vote

import io.kotest.core.spec.style.BehaviorSpec

class EndVoteNotificationServiceTest:BehaviorSpec({

    given("") {
        `when`("") {
            then("") {
            }
        }
    }

//    fun getNotifications(users: List<User>): List<Notification> {
//        val usersGroup: Map<UserClass, List<User>> = users.groupBy { UserClass.of(it) }
//        return usersGroup.mapValues { createNotification(it) }
//            .flatMap { it.value }
//    }
//
//    private fun createNotification(users: Map.Entry<UserClass, List<User>>): List<Notification> {
//        if (users.value.size == 1) {
//            return emptyList()
//        }
//
//        return users.value.map {
//            Notification.createVoteInitialState(
//                it.id,
//                NotificationType.VOTE_RESULT,
//                LocalDate.now().minusDays(1),
//                "우리 반 투표가 종료되었어요! 지난 투표의 1등은 누구였을까요?",
//            )
//        }
//    }

})
