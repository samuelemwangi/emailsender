package app.emailsender.unittest.application.core.errors

import app.emailsender.application.core.errors.queries.geterror.GetErrorQuery
import app.emailsender.application.core.errors.queries.geterror.GetErrorQueryHandler
import app.emailsender.application.enums.RequestStatus
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.infrastructure.utilities.DateTimeHelperService
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import reactor.test.StepVerifier

internal class GetErrorQueryHandlerTest : BaseTest() {

    private val dateTimeHelper: DateTimeHelper = DateTimeHelperService()

    @Test
    fun testErrorToDTO() {
        // Arrange
        val message = "Test error message"
        val httpStatus = HttpStatus.BAD_REQUEST
        val query = GetErrorQuery(message, httpStatus)

        // Act & Assert
        GetErrorQueryHandler(dateTimeHelper).toDTO(query).also {
            validateEquals(message, it.message, "message")
            validateEquals(dateTimeHelper.getTimeStamp().substring(0, 19), it.timestamp.substring(0, 19), "timestamp")
        }
    }

    @Test
    fun testGetErrorItem() {
        // Arrange
        val message = "Test error message"
        val httpStatus = HttpStatus.BAD_REQUEST
        val query = GetErrorQuery(message, httpStatus)

        // Act & Assert
        StepVerifier.create(GetErrorQueryHandler(dateTimeHelper).getItem(query))
            .expectNextMatches {
                validateEquals(RequestStatus.FAILED.labelText, it.requestStatus, "requestStatus")
                validateEquals(
                    httpStatus.toString().replace(" ", " - ").replace("_", " "),
                    it.statusMessage,
                    "statusMessage"
                )
                true
            }
            .verifyComplete()
    }
}
