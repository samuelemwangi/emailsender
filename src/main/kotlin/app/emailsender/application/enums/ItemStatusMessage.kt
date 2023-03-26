package app.emailsender.application.enums

enum class ItemStatusMessage(val labelText: String) {
    SUCCESS("Item(s) fetched successfully"),
    FAILED("Fetching item(s) failed"),
    NOTFOUND("The requested item(s) could not be found")
}