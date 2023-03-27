package app.emailsender.domain

import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

open class BaseEntity(
    @Column var createdAt: LocalDateTime? = null,
    @Column var createdBy: String? = null,
    @Column var updatedAt: LocalDateTime? = null,
    @Column var updatedBy: String? = null,
    @Column var deletedAt: LocalDateTime? = null
)