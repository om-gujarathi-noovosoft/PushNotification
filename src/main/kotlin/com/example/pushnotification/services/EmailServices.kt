package com.example.pushnotification.services

import com.example.pushnotification.models.Email
import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.repositories.EmailRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service

class EmailServices(
    val emailRepository: EmailRepository,
    val javaMailSender: JavaMailSender
) {

    @Value("\${spring.mail.username}")
    private val sender: String? = null
    fun addEmail(email: Email) {
        email.executionStatus = ExecutionStatus.QUEUED
        emailRepository.save(email)
    }

    fun fetchLatest() {
        sendSimpleMail(emailRepository.getLatestEmail())

    }

    fun sendSimpleMail(emailDetails: Email): String {

        return try {
            val mailMessage = SimpleMailMessage()

            mailMessage.setFrom(sender!!)
            mailMessage.setTo(emailDetails.receiverEmail)
            mailMessage.setText(emailDetails.message)
            javaMailSender?.send(mailMessage)
            "Mail Send"
        } catch (e: Exception) {
            "Error in Mail Sending $e"
        }


    }

}