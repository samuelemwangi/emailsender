package app.emailsender.application.exceptions

class InvalidOperationException(
    message: String
) : RuntimeException(message) {
    constructor(operation: String, className: String) : this("$className: The operation - $operation - is not allowed")
}
