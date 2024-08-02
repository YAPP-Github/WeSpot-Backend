package com.wespot.notification.domain.vote

import io.kotest.core.spec.style.BehaviorSpec

class RegisteredVoteNotificationServiceTest : BehaviorSpec({

    given("") {
        `when`("") {
            then("") {
            }
        }
    }

//    fun getNotifications(
//        sender: User,
//        users: List<User>,
//        vote: Vote
//    ): List<Notification> {
//        val numberOfSender = vote.getNumberOfSender()
//        if (isNotNotificationTrigger(numberOfSender)) {
//            return emptyList()
//        }
//        return users.filter { it.id != sender.id }
//            .map {
//                Notification.createVoteInitialState(
//                    it.id,
//                    NotificationType.VOTE_RESULT,
//                    LocalDate.now(),
//                    "업데이트 된 투표 분석 결과를 확인해 보세요!"
//                )
//            }
//    }
//
//    private fun isNotNotificationTrigger(numberOfSender: Int) =
//        numberOfSender < 5 || (numberOfSender - 5) % 3 != 0

})
