package app.emailsender.unittest.application.enums

import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test

internal class ItemStatusMessageTest : BaseTest() {
    @Test
    fun testItemStatusMessage() {
        // Arrange
        val createItemSuccessStatusMessage = ItemStatusMessage.CREATE_ITEM_SUCCESSFUL
        val updateItemSuccessStatusMessage = ItemStatusMessage.UPDATE_ITEM_SUCCESSFUL
        val deleteItemSuccessStatusMessage = ItemStatusMessage.DELETE_ITEM_SUCCESSFUL
        val fetchItemFailedStatusMessage = ItemStatusMessage.FETCH_ITEM_FAILED
        val fetchItemsFailedStatusMessage = ItemStatusMessage.FETCH_ITEMS_FAILED
        val fetchItemSuccessfulStatusMessage = ItemStatusMessage.FETCH_ITEM_SUCCESSFUL
        val fetchItemsSuccessfulStatusMessage = ItemStatusMessage.FETCH_ITEMS_SUCCESSFUL
        val itemNotFoundStatusMessage = ItemStatusMessage.ITEM_NOT_FOUND

        // Act & Assert
        validateEquals(
            "Item created successfully",
            createItemSuccessStatusMessage.labelText,
            "createItemSuccessStatusMessage.labelText"
        )
        validateEquals(
            "Item updated successfully",
            updateItemSuccessStatusMessage.labelText,
            "updateItemSuccessStatusMessage.labelText"
        )
        validateEquals(
            "Item deleted successfully",
            deleteItemSuccessStatusMessage.labelText,
            "deleteItemSuccessStatusMessage.labelText"
        )
        validateEquals(
            "Fetching item failed",
            fetchItemFailedStatusMessage.labelText,
            "fetchItemFailedStatusMessage.labelText"
        )
        validateEquals(
            "Fetching items failed",
            fetchItemsFailedStatusMessage.labelText,
            "fetchItemsFailedStatusMessage.labelText"
        )
        validateEquals(
            "Item fetched successfully",
            fetchItemSuccessfulStatusMessage.labelText,
            "fetchItemSuccessfulStatusMessage.labelText"
        )
        validateEquals(
            "Items fetched successfully",
            fetchItemsSuccessfulStatusMessage.labelText,
            "fetchItemsSuccessfulStatusMessage.labelText"
        )
        validateEquals(
            "The requested item could not be found",
            itemNotFoundStatusMessage.labelText,
            "itemNotFoundStatusMessage.labelText"
        )
    }
}
