package com.example.pushnotification.repositories

import com.example.pushnotification.models.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailRepository : JpaRepository<Email, Long>

