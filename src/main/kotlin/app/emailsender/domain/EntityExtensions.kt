package app.emailsender.domain

import java.time.LocalDateTime

fun BaseEntity.setAuditFields(userId: String?, currentDateTime: LocalDateTime) {
    this.createdAt = currentDateTime
    this.createdBy = userId
    this.updatedAt = currentDateTime
    this.updatedBy = userId
}

fun BaseEntity.updateAuditFields(userId: String?, currentDateTime: LocalDateTime) {
    this.updatedAt = currentDateTime
    this.updatedBy = userId
}