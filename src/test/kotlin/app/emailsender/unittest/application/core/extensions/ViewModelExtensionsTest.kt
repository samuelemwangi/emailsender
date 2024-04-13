package app.emailsender.unittest.application.core.extensions

import app.emailsender.application.core.BaseViewModel
import app.emailsender.application.core.ItemDetailBaseViewModel
import app.emailsender.application.core.ItemsBaseViewModel
import app.emailsender.application.core.extensions.resolveCreateDownloadRights
import app.emailsender.application.core.extensions.resolveEditDeleteRights
import app.emailsender.application.core.extensions.resolveRequestStatus
import app.emailsender.application.core.extensions.resolveStatusMessage
import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.application.enums.RequestStatus
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test

internal class ViewModelExtensionsTest : BaseTest() {

    class TestBaseVM : BaseViewModel()
    class TestItemBaseVM : ItemsBaseViewModel()
    class TestItemDetailBaseVM : ItemDetailBaseViewModel()

    @Test
    fun testResolveRequestStatus() {
        // Arrange
        val testBaseVM = TestBaseVM()

        // Act
        testBaseVM.resolveRequestStatus(RequestStatus.SUCCESSFUL)

        // Assert
        validateEquals(RequestStatus.SUCCESSFUL.labelText, testBaseVM.requestStatus, "requestStatus")
    }

    @Test
    fun testResolveStatusMessage() {
        // Arrange
        val testBaseVM = TestBaseVM()

        // Act
        testBaseVM.resolveStatusMessage(ItemStatusMessage.UPDATE_ITEM_SUCCESSFUL)

        // Assert
        validateEquals(ItemStatusMessage.UPDATE_ITEM_SUCCESSFUL.labelText, testBaseVM.statusMessage, "statusMessage")
    }

    @Test
    fun testResolveEditDeleteRights() {
        // Arrange
        val testItemDetailBaseVM = TestItemDetailBaseVM()
        val entity = "Test"
        val userScopes = "test:edit, test:delete"

        val testItemDetailBaseVM2 = TestItemDetailBaseVM()
        val entity2 = "Email"

        // Act
        testItemDetailBaseVM.resolveEditDeleteRights(userScopes, entity, true)
        testItemDetailBaseVM2.resolveEditDeleteRights(userScopes, entity2, false)

        // Assert
        validateEquals(true, testItemDetailBaseVM.editEnabled)
        validateEquals(true, testItemDetailBaseVM.deleteEnabled)

        validateEquals(false, testItemDetailBaseVM2.editEnabled)
        validateEquals(false, testItemDetailBaseVM2.deleteEnabled)
    }

    @Test
    fun testResolveCreateDownloadRights() {
        // Arrange
        val testItemBaseVM = TestItemBaseVM()
        val entity = "Test"
        val userScopes = "test:create, test:download"

        val testItemBaseVM2 = TestItemBaseVM()
        val entity2 = "Email"

        // Act
        testItemBaseVM.resolveCreateDownloadRights(userScopes, entity)
        testItemBaseVM2.resolveCreateDownloadRights(userScopes, entity2)

        // Assert
        validateEquals(true, testItemBaseVM.createEnabled)
        validateEquals(true, testItemBaseVM.downloadEnabled)

        validateEquals(false, testItemBaseVM2.createEnabled)
        validateEquals(false, testItemBaseVM2.downloadEnabled)
    }
}
