package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.repositories.NotificationRepository
import com.example.pushnotification.viewmodels.NotificationViewModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class NotificationService(val notificationRepository: NotificationRepository) {

    @Autowired
    private val javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String = ""

    fun getNotifications(): List<Notification> {
       return notificationRepository.findNotificationByExecutionStatus(ExecutionStatus.QUEUED).toList()
    }

    fun addEmail(notificationViewModel: NotificationViewModel) {
        notificationRepository.save(
            Notification(
                receiverEmail = notificationViewModel.receiverEmail,
                subject = notificationViewModel.subject,
                message = notificationViewModel.message,
                senderEmail = sender,
                messageType = "Email",
                executionStatus = ExecutionStatus.QUEUED
            )
        )
        for (i in 1..50) {
            addRandomData()
        }
    }

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun addRandomData() {
        notificationRepository.save(
            Notification(
                receiverEmail = getRandomString(15),
                subject = getRandomString(10),
                message = getRandomString(20),
                senderEmail = sender,
                messageType = "Email",
                executionStatus = ExecutionStatus.QUEUED
            )
        )
    }

    fun fetchNewData(): List<Notification> {
        return notificationRepository.findNotificationByExecutionStatus(ExecutionStatus.QUEUED)
    }
    fun sendSimpleMail(notificationDetails: Notification): ExecutionStatus {
        return try {
            val mailMessage = SimpleMailMessage()

            mailMessage.setFrom(sender!!)
            mailMessage.setTo(notificationDetails.receiverEmail)
            mailMessage.setText(notificationDetails.message)
            mailMessage.setSubject(notificationDetails.subject)
            javaMailSender?.send(mailMessage)
            ExecutionStatus.SENT
        } catch (e: Exception) {
            ExecutionStatus.FAILED
        }
    }

}