package com.example.pushnotification.controllers

import com.example.pushnotification.services.EmailService
import com.example.pushnotification.viewmodels.EmailViewModel
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("email")
class EmailController(
    val emailService: EmailService
) {
    @PostMapping
    fun addEmailRequest(@RequestBody emailViewModel: EmailViewModel) {
        return emailService.addEmail(emailViewModel)
    }
}