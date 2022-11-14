package com.example.pushnotification.controllers
import com.example.pushnotification.models.Email
import com.example.pushnotification.services.EmailService
import org.springframework.web.bind.annotation.*


@RestController
class EmailController(
    val emailService: EmailService
) {
    @PostMapping
    fun addEmailRequest(@RequestBody email: Email){
        return emailService.addEmail(email)
    }
}