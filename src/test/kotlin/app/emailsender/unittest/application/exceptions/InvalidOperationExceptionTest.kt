package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.InvalidOperationException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class InvalidOperationExceptionTest : BaseTest() {
    @Test
    fun testThrowInvalidOperationException() {
        // Arrange
        val expectedMessage1 = "Invalid Operation Exception"
        val expectedException1 = InvalidOperationException(expectedMessage1)

        val className = "Test"
        val operation = "Update"
        val expectedMessage2 = "$className: The operation - $operation - is not allowed"
        val expectedException2 = InvalidOperationException(operation, className)

        // Act
        val exceptionFun1: () -> Unit = { throw expectedException1 }
        val exceptionFun2: () -> Unit = { throw expectedException2 }

        // Assert
        val actualException1 = assertThrows<RuntimeException> { exceptionFun1() }
        validateEquals(expectedMessage1, actualException1.message, "message")
        validateEquals(true, actualException1 is InvalidOperationException)

        val actualException2 = assertThrows<RuntimeException> { exceptionFun2() }
        validateEquals(expectedMessage2, actualException2.message, "message")
        validateEquals(true, actualException2 is InvalidOperationException)
    }
}
