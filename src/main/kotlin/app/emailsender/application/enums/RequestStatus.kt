package app.emailsender.application.enums

enum class RequestStatus(val labelText: String) {
    SUCCESSFUL("Request successful"),
    FAILED("Request failed"),
    ABORTED("Request aborted");
}
