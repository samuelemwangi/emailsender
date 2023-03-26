package app.emailsender.domain

import java.time.LocalDateTime

open class BaseEntity {
    var createdAt: LocalDateTime? = null
    var createdBy: String? = null
    var updatedAt: LocalDateTime? = null
    var updatedBy: String? = null
    var deletedAt: LocalDateTime? = null
}