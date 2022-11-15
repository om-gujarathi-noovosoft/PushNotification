package com.example.pushnotification.services

import com.example.pushnotification.models.Notification
import com.example.pushnotification.repositories.NotificationRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailSender(private   val notificationService: NotificationService, val notificationRepository: NotificationRepository) {
    val tasks: MutableList<Notification> = notificationService.fetchNewData().toMutableList()
    var counter1 = 0
    var counter2 = 0

    @Scheduled(fixedRate = 1000)
    fun scheduleTaskWithFixedRateT1() {
        if (tasks.size > 0) {
            val currentTask = tasks.first()
            currentTask.executionStatus = notificationService.sendSimpleMail(currentTask)
            notificationRepository.save(currentTask)
            tasks.removeAt(0)
            counter1++
        } else {
            tasks.addAll(notificationService.fetchNewData())
            if (tasks.size < 30) {
                Thread.sleep(10000)
                println("Counter 1 = $counter1")
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    fun scheduleTaskWithFixedRateT2() {
        if (tasks.size > 0) {
            val currentTask = tasks.first()
            currentTask.executionStatus = notificationService.sendSimpleMail(currentTask)
            notificationRepository.save(currentTask)
            tasks.removeAt(0)
            counter2++
        } else {
            tasks.addAll(notificationService.fetchNewData())
            if (tasks.size < 30) {
                Thread.sleep(10000)
                println("Counter 2 = $counter2")
            }
        }
    }
}