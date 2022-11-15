package com.example.pushnotification.services

import com.example.pushnotification.models.ExecutionStatus
import com.example.pushnotification.models.Notification
import com.example.pushnotification.repositories.NotificationRepository
import java.util.*

class TaskScheduler(
    private val notificationRepository: NotificationRepository?,
    private val sendEmailThread1: SendEmailThread1?,
    private val sendEmailThread2: SendEmailThread2?
) : Thread() {

    override fun run() {
        val tasks: Queue<Notification> = LinkedList<Notification>()
        notificationRepository?.let { tasks.addAll(it.findEmailByExecutionStatus(ExecutionStatus.QUEUED)) }
        if (tasks.size > 30) {
            if (!sendEmailThread1?.isAlive!!) {
                val task = tasks.poll()
                sendEmailThread1?.sendEmailThread1(task)
                println("1")
            }
            if (!sendEmailThread2?.isAlive!!) {
                val task = tasks.poll()
                sendEmailThread2?.sendEmailThread2(task)
                println("2")
            }
        } else {
            notificationRepository?.let { tasks.addAll(it.findEmailByExecutionStatus(ExecutionStatus.QUEUED)) }
        }
    }
}