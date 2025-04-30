package com.wespot.notification.service

import com.wespot.notification.port.`in`.VoteNotificationUseCase
import com.wespot.notification.port.out.NotificationPort
import com.wespot.notification.vote.EncourageVoteNotificationService
import com.wespot.notification.vote.EndVoteNotificationService
import com.wespot.notification.vote.ReceivedVoteNotificationService
import com.wespot.notification.vote.RegisteredVoteNotificationService
import com.wespot.notification.vote.SignUpVoteNotificationService
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatedVoteNotificationService(
    private val userPort: UserPort,
    private val notificationPort: NotificationPort,
    private val encourageVoteNotificationService: EncourageVoteNotificationService,
    private val signUpVoteNotificationService: SignUpVoteNotificationService,
    private val receivedVoteNotificationService: ReceivedVoteNotificationService,
    private val registeredVoteNotificationService: RegisteredVoteNotificationService,
    private val endVoteNotificationService: EndVoteNotificationService,
    private val notificationHelper: NotificationHelper
) : VoteNotificationUseCase {

    @Transactional
    override fun encourageVote() {
        val users = userPort.findAll()
        val notifications = encourageVoteNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun signUpUser(signUpUser: User) {
        val users = userPort.findAllBySchoolIdAndGradeAndClassNumber(
            signUpUser.school.id,
            signUpUser.grade,
            signUpUser.classNumber
        )
        val notifications = signUpVoteNotificationService.getNotifications(signUpUser, users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun registerVote(numberOfSenderBeforeVote: Int, sender: User, vote: Vote) {
        val getUsers =
            { userPort.findAllBySchoolIdAndGradeAndClassNumber(sender.school.id, sender.grade, sender.classNumber) }
        val (notifications, users) =
            registeredVoteNotificationService.getNotifications(numberOfSenderBeforeVote, sender, getUsers, vote)
        notificationPort.saveAll(notifications)
        notificationHelper.sendNotifications(users, notifications)
    }

    @Transactional
    override fun endVote() {
        val users = userPort.findAll()
        val notifications = endVoteNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun receiveVote(sender: User, receiver: User) {
        val notification = receivedVoteNotificationService.getNotification(receiver.id, sender.gender)
        notificationPort.save(notification)
        notificationHelper.sendNotification(receiver, notification)
    }

}
