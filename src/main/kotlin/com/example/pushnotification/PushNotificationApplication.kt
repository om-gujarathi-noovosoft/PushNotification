package com.example.pushnotification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class PushNotificationApplication

fun main(args: Array<String>) {
    runApplication<PushNotificationApplication>(*args)
}
