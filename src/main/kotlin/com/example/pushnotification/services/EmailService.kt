package com.example.pushnotification.services

import com.example.pushnotification.models.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService {

    @Autowired
    private val javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String? = null

    fun sendSimpleMail(emailDetails: Email): String {

        return try {
            val mailMessage = SimpleMailMessage()

            mailMessage.setFrom(sender!!)
            mailMessage.setTo(emailDetails.receiverEmail)
            mailMessage.setText(emailDetails.message)
            mailMessage.setSubject(emailDetails.subject)

            javaMailSender?.send(mailMessage)
            "Mail Send"
        } catch (e: Exception) {
            "Error in Mail Sending $e"
        }


    }

}