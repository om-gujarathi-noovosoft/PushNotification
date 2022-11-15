package com.example.pushnotification.repositories

import com.example.pushnotification.models.Notification
import com.example.pushnotification.models.ExecutionStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findNotificationByExecutionStatus(executionStatus: ExecutionStatus):List<Notification>
}

