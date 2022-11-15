package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.repositories.NotificationRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailSender(val notificationService: NotificationService, val notificationRepository: NotificationRepository) {
    val tasks: MutableList<Notification> = notificationService.fetchNewData().toMutableList()

    @Scheduled(fixedRate = 1000)
    fun scheduleTaskWithFixedRateT1() {
        if (tasks.size > 30) {
            val currentTask = tasks.first()
            currentTask.executionStatus = notificationService.sendSimpleMail(currentTask)
            notificationRepository.save(currentTask)
            tasks.removeAt(0)
            println("T1")

        } else {
            tasks.addAll(notificationService.fetchNewData())
            if (tasks.size < 30) {
                Thread.sleep(10000)
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    fun scheduleTaskWithFixedRateT2() {
        if (tasks.size > 30) {
            val currentTask = tasks.first()
            currentTask.executionStatus = notificationService.sendSimpleMail(currentTask)
            notificationRepository.save(currentTask)
            tasks.removeAt(0)
            println("T2")
        } else {
            tasks.addAll(notificationService.fetchNewData())
            if (tasks.size < 30) {
                Thread.sleep(10000)
            }
        }
    }
}