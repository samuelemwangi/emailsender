package app.emailsender.application.exceptions

class DatabaseOperationException (
    message: String
) : RuntimeException(message) {
    constructor(operation: String, className: String) : this("$className: An error occurred performing action - $operation")
}