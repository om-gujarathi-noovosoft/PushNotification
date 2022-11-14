package com.example.pushnotification.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.stereotype.Service

@Service
class Worker(private val scheduledAnnotationBeanPostProcessor: ScheduledAnnotationBeanPostProcessor,val emailServices: EmailServices) {
    @Scheduled(fixedRate = 5000L)
    fun start() {
        emailServices.fetchLatest()
    }

    fun stop() = scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(this, "Worker")
}