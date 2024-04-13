package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.BadRequestException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BadRequestExceptionTest : BaseTest() {
    @Test
    fun testThrowBadRequestException() {
        // Arrange
        val exceptionMessage = "Bad Request"
        val expectedException = BadRequestException(exceptionMessage)

        val exceptionFun: () -> Unit = { throw expectedException }

        // Act & Assert
        val actualException = assertThrows<RuntimeException> { exceptionFun() }
        validateEquals(expectedException.message, actualException.message, "message")
        validateEquals(true, actualException is BadRequestException)
    }
}
