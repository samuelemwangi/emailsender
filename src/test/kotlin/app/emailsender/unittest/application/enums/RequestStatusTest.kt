package app.emailsender.unittest.application.enums

import app.emailsender.application.enums.RequestStatus
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test

internal class RequestStatusTest : BaseTest() {
    @Test
    fun testRequestStatus() {
        // Arrange
        val requestStatusSuccess = RequestStatus.SUCCESSFUL
        val requestStatusFailed = RequestStatus.FAILED
        val requestStatusAborted = RequestStatus.ABORTED

        // Act & Assert
        validateEquals("Request successful", requestStatusSuccess.labelText, "requestStatusSuccess.labelText")
        validateEquals("Request failed", requestStatusFailed.labelText, "requestStatusFailed.labelText")
        validateEquals("Request aborted", requestStatusAborted.labelText, "requestStatusAborted.labelText")
    }
}
