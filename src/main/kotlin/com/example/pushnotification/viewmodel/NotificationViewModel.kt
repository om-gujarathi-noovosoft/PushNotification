package com.example.pushnotification.viewmodel

import com.example.pushnotification.models.NotificationType

data class NotificationViewModel(
    val senderEmail: String,
    val receiverEmail: String,
    val subject: String,
    val message: String,
    val messageType: String?,
    val type: NotificationType,
)