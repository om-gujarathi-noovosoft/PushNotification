package com.example.pushnotification

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.stereotype.Service

@Service
class Worker(private val scheduledAnnotationBeanPostProcessor: ScheduledAnnotationBeanPostProcessor) {
    @Scheduled(fixedRate = 5000L)
    fun start() {
        println("call this every time")
    }

    fun stop() = scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(this, "Worker")
}