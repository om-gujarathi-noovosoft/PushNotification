package com.example.pushnotification.controllers
import com.example.pushnotification.models.Notification
import com.example.pushnotification.services.NotificationService
import org.springframework.web.bind.annotation.*


@RestController
class NotificationController(
    val notificationService: NotificationService
) {
    @PostMapping
    fun addNotificationRequest(@RequestBody notification: Notification){
        return notificationService.addNotification(notification)
    }
}