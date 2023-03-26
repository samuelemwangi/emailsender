package app.emailsender.application.exceptions

class BadRequestException(
    errorMessage: String
) : RuntimeException(errorMessage)