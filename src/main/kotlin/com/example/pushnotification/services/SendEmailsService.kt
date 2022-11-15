package com.example.pushnotification.services

import com.example.pushnotification.models.Notification

class SendEmailThread1(private val notificationService: NotificationService) : Thread() {

    override fun run() {

    }

    fun sendEmailThread1(notification: Notification) {
        notificationService.sendSimpleMail(notification)
    }
}

class SendEmailThread2(private val notificationService: NotificationService) : Thread() {
    override fun run() {

    }

    fun sendEmailThread2(notification: Notification) {
        notificationService.sendSimpleMail(notification)
    }

}

