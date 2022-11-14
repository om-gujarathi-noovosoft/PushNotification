package com.example.pushnotification.repositories

import com.example.pushnotification.models.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmailRepository : JpaRepository<Email, Long> {
    @Query(
        """
            select * from email e where e.execution_status='QUEUED' LIMIT 1
        """,
        nativeQuery = true
    )
    fun getLatestEmail():Email
}