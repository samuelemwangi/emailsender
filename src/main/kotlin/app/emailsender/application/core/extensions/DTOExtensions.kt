package app.emailsender.application.core.extensions

import app.emailsender.application.core.BaseEntityDTO
import app.emailsender.domain.BaseEntity
import java.time.LocalDateTime

fun BaseEntityDTO.setDTOAuditFields(entity: BaseEntity, dateResolver: (LocalDateTime?) -> String) {
    this.createdAt = dateResolver(entity.createdAt)
    this.createdBy = entity.createdBy ?: ""
    this.updatedAt = dateResolver(entity.updatedAt)
    this.updatedBy = entity.updatedBy ?: ""
}

fun BaseEntityDTO.recordOwnedByCurrentUser(userid: String?): Boolean {
    return userid != null && (this.createdBy == userid || this.updatedBy == userid)
}
