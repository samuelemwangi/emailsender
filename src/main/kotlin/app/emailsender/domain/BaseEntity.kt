package app.emailsender.domain

import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

abstract class BaseEntity(
    @Column
    var createdAt: LocalDateTime? = null,
    @Column
    var createdBy: String? = null,
    @Column
    var updatedAt: LocalDateTime? = null,
    @Column
    var updatedBy: String? = null,
    @Column
    val deletedAt: LocalDateTime? = null
)
