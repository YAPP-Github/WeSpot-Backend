package com.wespot.notification.vote

import com.wespot.notification.ClassmateValidator
import com.wespot.notification.Notification
import com.wespot.notification.NotificationType
import com.wespot.user.User
import com.wespot.vote.Vote
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RegisteredVoteNotificationService {

    fun getNotifications(
        numberOfSenderBeforeVote: Int,
        sender: User,
        getUsers: () -> List<User>,
        vote: Vote
    ): Pair<List<Notification>, List<User>> {
        val numberOfSender = vote.getNumberOfSender()
        if (numberOfSenderBeforeVote == numberOfSender || isNotNotificationTrigger(numberOfSender)) {
            return Pair(emptyList(), emptyList())
        }
        val users = getUsers.invoke()
        ClassmateValidator.validateContainNonClassmateUser(users)
        val notifications = users.filter { it.id != sender.id }
            .map {
                Notification.createVoteInitialState(
                    it.id,
                    NotificationType.VOTE_RESULT,
                    LocalDate.now(),
                    "우리 반 투표 결과가 업데이트 되었어요 \uD83D\uDC40",
                    "실시간 1등은 누구일까요? 눌러서 바로 확인해 보세요"
                )
            }
        return Pair(notifications, users)
    }

    private fun isNotNotificationTrigger(numberOfSender: Int) =
        numberOfSender < 5 || (numberOfSender - 5) % 3 != 0

}
