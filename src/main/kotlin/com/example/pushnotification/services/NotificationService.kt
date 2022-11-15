package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.repositories.NotificationRepository
import com.example.pushnotification.viewmodel.NotificationViewModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service

class NotificationService(
    val notificationRepository: NotificationRepository, val javaMailSender: JavaMailSender
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
    }

    fun fetchLatest() {
        if (notificationRows.isEmpty()) {
            notificationRows = notificationRepository.getLatestNotification()
            while (notificationRows.isNotEmpty()) {
                sendSimpleMail(notificationRows.first())
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
            javaMailSender?.send(mailMessage)
        } catch (e: Exception) {
            false
        }
        notificationDetails.executionStatus = ExecutionStatus.SENT
        notificationRepository.save(notificationDetails)
        return true
    }

}