package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.repositories.NotificationRepository
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
    fun addNotification(notification: Notification) {
        notification.executionStatus = ExecutionStatus.QUEUED
        notificationRepository.save(notification)
    }

    fun fetchLatest() {
        if (notificationRows.isEmpty()) {
            notificationRows = notificationRepository.getLatestEmail()
            while(notificationRows.isNotEmpty()) {
                if (sendSimpleMail(notificationRows[0])) {
                    notificationRows.drop(0)
                }
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