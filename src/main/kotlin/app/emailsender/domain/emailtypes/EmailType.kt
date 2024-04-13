package app.emailsender.domain.emailtypes

import app.emailsender.domain.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.annotation.processing.Generated

@Table("email_types")
data class EmailType(
    @Id
    @Generated
    var id: Int = 0,

    @Column
    var type: String,

    @Column
    var description: String?,
) : BaseEntity()
