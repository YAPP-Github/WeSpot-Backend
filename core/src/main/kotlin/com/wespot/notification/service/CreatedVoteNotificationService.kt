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
            signUpUser.schoolId,
            signUpUser.grade,
            signUpUser.classNumber
        )
        val notifications = signUpVoteNotificationService.getNotifications(signUpUser, users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun registerVote(sender: User, vote: Vote) {
        val users = userPort.findAllBySchoolIdAndGradeAndClassNumber(sender.schoolId, sender.grade, sender.classNumber)
        val notifications = registeredVoteNotificationService.getNotifications(sender, users, vote)
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
    override fun receiveVote(receiver: User) {
        val notification = receivedVoteNotificationService.getNotification(receiver.id, receiver.gender)
        notificationPort.save(notification)
        notificationHelper.sendNotification(receiver, notification)
    }

}
