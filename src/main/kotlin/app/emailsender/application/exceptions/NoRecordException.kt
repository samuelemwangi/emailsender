package app.emailsender.application.exceptions

class NoRecordException (
    message: String
) : RuntimeException(message) {
    constructor(id: String, className: String) : this("$className: No record exists for the provided identifier - $id")
}