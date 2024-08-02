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
    private val notificationServiceHelper: NotificationServiceHelper
) : VoteNotificationUseCase {

    @Transactional
    override fun encourageVote() {
        val users = userPort.findAll()
        val notifications = encourageVoteNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationServiceHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun signUpUser(user: User) {
        val users = userPort.findAllBySchoolIdAndGradeAndClassNumber(user.schoolId, user.grade, user.classNumber)
        val notifications = signUpVoteNotificationService.getNotifications(user, users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationServiceHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun registerVote(sender: User, vote: Vote) {
        val users = userPort.findAllBySchoolIdAndGradeAndClassNumber(sender.schoolId, sender.grade, sender.classNumber)
        val notifications = registeredVoteNotificationService.getNotifications(sender, users, vote)
        notificationPort.saveAll(notifications)
        notificationServiceHelper.sendMulticastNotification(users, notifications)
    }

    @Transactional
    override fun endVote() {
        val users = userPort.findAll()
        val notifications = endVoteNotificationService.getNotifications(users)
        notificationPort.saveAll(notifications)
        val usersGroup = users.associateBy { it.id }
        notifications.forEach { notificationServiceHelper.sendNotification(usersGroup[it.userId], it) }
    }

    @Transactional
    override fun receiveVote(user: User) {
        val notification = receivedVoteNotificationService.getNotification(user.id, user.gender)
        notificationPort.save(notification)
        notificationServiceHelper.sendNotification(user, notification)
    }

}
