package com.example.pushnotification

import com.example.pushnotification.repositories.NotificationRepository
import com.example.pushnotification.services.SendEmailThread1
import com.example.pushnotification.services.SendEmailThread2
import com.example.pushnotification.services.TaskScheduler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PushNotificationApplication {

}

fun main(args: Array<String>) {
    runApplication<PushNotificationApplication>(*args)
    val taskScheduler = TaskScheduler(null, null, null)
    taskScheduler.start()
}
