package com.example.pushnotification.services

import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.models.Notification
import com.example.pushnotification.repositories.NotificationRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailSender(val notificationService: NotificationService, val notificationRepository: NotificationRepository) {
    val tasks: MutableList<Notification> = notificationService.fetchNewData().toMutableList()

    @Scheduled(fixedRate = 2000)
    fun scheduleTaskWithFixedRate() {
        if (tasks.size > 30) {
            val currentTask = tasks.first()
            currentTask.executionStatus = notificationService.sendSimpleMail(currentTask)
            notificationRepository.save(currentTask)
        } else {
            tasks.addAll(notificationService.fetchNewData())
        }
    }
}