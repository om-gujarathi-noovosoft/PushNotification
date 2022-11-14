package com.example.pushnotification.controllers
import com.example.pushnotification.models.Email
import com.example.pushnotification.services.EmailServices
import org.springframework.web.bind.annotation.*


@RestController
class EmailController(
    val emailServices: EmailServices
) {
    @PostMapping
    fun addEmailRequest(@RequestBody email: Email){
        return emailServices.addEmail(email)
    }
}