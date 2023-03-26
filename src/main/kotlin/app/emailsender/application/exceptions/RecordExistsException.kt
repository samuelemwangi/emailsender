package app.emailsender.application.exceptions

class RecordExistsException(
    message: String
) : RuntimeException(message) {
    constructor(id: String, className: String) : this("$className: A record identified with - $id - exists")
}