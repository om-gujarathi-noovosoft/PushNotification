package com.example.pushnotification.models

import org.hibernate.annotations.CreationTimestamp
import javax.persistence.*

@Entity
@Table(name = "email")
data class Email(
    @Column
    val senderEmail: String,

    @Column
        val receiverEmail: String,

    @Column
    val subject: String,

    @Column
    val message: String,

    @Column
    val messageType: String,

    @Column
    @Enumerated(EnumType.STRING)
    val executionStatus: ExecutionStatus
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    val time: java.sql.Time = java.sql.Time(System.currentTimeMillis())

}

enum class ExecutionStatus {
    SENT, QUEUED, SENDING
}