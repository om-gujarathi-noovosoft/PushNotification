package com.example.pushnotification.controllers
import com.example.pushnotification.models.Notification
import com.example.pushnotification.services.NotificationService
import com.example.pushnotification.viewmodel.NotificationViewModel
import org.springframework.web.bind.annotation.*


@RestController
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping("/notification")
    fun addNotificationRequest(@RequestBody notification: NotificationViewModel){
        return notificationService.addNotification(notification)
    }
}