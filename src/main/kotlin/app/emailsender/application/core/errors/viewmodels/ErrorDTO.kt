package app.emailsender.application.core.errors.viewmodels

import java.time.Instant

class ErrorDTO(
    val message: String,
    val timestamp: Instant
)