package com.example.pushnotification.services

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadFactory

class ApplicationsThreadFactory(private val threadName: String) : ThreadFactory {
    override fun newThread(r: Runnable): Thread = Thread(r, threadName)
}

@Service
class ThreadHandler(notificationService: NotificationService, environment: Environment) {
    init {
        val threadCount: Int = environment.getProperty("thread.count")?.toInt() ?: 2
        for (i in 0..threadCount) {
            ApplicationsThreadFactory("worker $i").newThread(Task(notificationService, environment)).start()
        }
    }
}

