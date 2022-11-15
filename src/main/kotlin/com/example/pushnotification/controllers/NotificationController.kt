package com.example.pushnotification.controllers

import com.example.pushnotification.services.NotificationService
import com.example.pushnotification.viewmodels.NotificationViewModel
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("email")
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping
    fun addEmailRequest(@RequestBody notificationViewModel: NotificationViewModel) {
        return notificationService.addEmail(notificationViewModel)
    }
}