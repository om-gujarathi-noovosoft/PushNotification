package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.models.NotificationType
import com.example.pushnotification.repositories.NotificationRepository
import com.example.pushnotification.viewmodel.NotificationViewModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class NotificationService(
    val notificationRepository: NotificationRepository, val javaMailSender: JavaMailSender,@Lazy val worker: Worker
) {

    @Value("\${spring.mail.username}")
    private val sender: String? = null
    var notificationRows = listOf<Notification>()
    fun addNotification(notification: NotificationViewModel) {
        notificationRepository.save(
            Notification(
                notification.senderEmail,
                notification.receiverEmail,
                notification.subject,
                notification.message,
                notification.messageType,
                notification.type,
                ExecutionStatus.QUEUED
            )
        )
        worker.start()
    }

    fun fetchLatest() {
        if (notificationRows.isEmpty()) {
            notificationRows = notificationRepository.getLatestNotification()
            if (notificationRows.isEmpty()) {
                worker.stop()
            }
            while (notificationRows.isNotEmpty()) {
                val notificationFirstRow = notificationRows.first()
                when (notificationFirstRow.type) {
                    NotificationType.EMAIL -> sendSimpleMail(notificationFirstRow)
                }
                notificationRows = notificationRows.drop(1)
            }
        }
    }

    fun sendSimpleMail(notificationDetails: Notification): Boolean {

        try {
            val mailMessage = SimpleMailMessage()
            mailMessage.setFrom(sender!!)
            mailMessage.setTo(notificationDetails.receiverEmail)
            mailMessage.setText(notificationDetails.message)
            mailMessage.setSubject(notificationDetails.subject)
            javaMailSender.send(mailMessage)
        } catch (e: Exception) {
            false
        }
        notificationDetails.executionStatus = ExecutionStatus.SENT
        notificationRepository.save(notificationDetails)
        return true
    }

}