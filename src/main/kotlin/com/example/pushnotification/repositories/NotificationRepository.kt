package com.example.pushnotification.repositories

import com.example.pushnotification.models.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    @Query(
        """
            select *
            from notification n 
            where n.execution_status='QUEUED'
            limit 1
        """,
        nativeQuery = true
    )
    fun getLatestNotification(): List<Notification>
}