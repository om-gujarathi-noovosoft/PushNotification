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
            from email e 
            where e.execution_status='QUEUED'
            order by e.time
            fetch first row only 
        """,
        nativeQuery = true
    )
    fun getLatestEmail(): List<Notification>
}