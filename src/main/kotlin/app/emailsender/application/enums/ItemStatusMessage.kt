package app.emailsender.application.enums

enum class ItemStatusMessage(val labelText: String) {
    CREATE_ITEM_SUCCESSFUL("Item created successfully"),

    UPDATE_ITEM_SUCCESSFUL("Item updated successfully"),

    FETCH_ITEM_SUCCESSFUL("Item fetched successfully"),
    FETCH_ITEMS_SUCCESSFUL("Items fetched successfully"),
    FETCH_ITEMS_SUCCESSFUL_NO_ITEMS("No items found"),

    DELETE_ITEM_SUCCESSFUL("Item deleted successfully"),

    FETCH_ITEM_FAILED("Fetching item failed"),
    FETCH_ITEMS_FAILED("Fetching items failed"),

    ITEM_NOT_FOUND("The requested item could not be found");
}
