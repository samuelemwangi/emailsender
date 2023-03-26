package app.emailsender.application.exceptions

class IllegalEventException (
    message: String
) : RuntimeException(message) {
    constructor(operation: String, className: String) : this("$className: The event - $operation - is not allowed")
}