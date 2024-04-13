package app.emailsender.application.core

abstract class BaseEntityDTO(
    var createdBy: String? = null,
    var createdAt: String? = null,
    var updatedBy: String? = null,
    var updatedAt: String? = null
)
