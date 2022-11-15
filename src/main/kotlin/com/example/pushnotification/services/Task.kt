package com.example.pushnotification.services

import org.springframework.core.env.Environment

class Task(private val notificationService: NotificationService, environment: Environment) : Runnable {
    private val sleepTime: Long = environment.getProperty("thread.sleep.time")?.toLong() ?: 5000L
    override fun run() {
        while (true) {
            notificationService.fetchLatest()
            Thread.sleep(sleepTime)
        }
    }
}