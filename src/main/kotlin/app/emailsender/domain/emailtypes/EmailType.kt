package app.emailsender.domain.emailtypes

import app.emailsender.domain.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("email_types")
data class EmailType(
    @Id var id: Int,
    var type: String,
    var description: String?,
) : BaseEntity()