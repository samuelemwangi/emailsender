package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.IllegalEventException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class IllegalEventExceptionTest : BaseTest() {
    @Test
    fun testThrowIllegalEventException() {
        // Arrange
        val expectedMessage1 = "Illegal Event Exception"
        val expectedException1 = IllegalEventException(expectedMessage1)

        val className = "Test"
        val operation = "Update"
        val expectedMessage2 = "$className: The event - $operation - is not allowed"
        val expectedException2 = IllegalEventException(operation, className)

        // Act
        val exceptionFun1: () -> Unit = { throw expectedException1 }
        val exceptionFun2: () -> Unit = { throw expectedException2 }

        // Assert
        val actualException1 = assertThrows<RuntimeException> { exceptionFun1() }
        validateEquals(expectedMessage1, actualException1.message, "message")
        validateEquals(true, actualException1 is IllegalEventException)

        val actualException2 = assertThrows<RuntimeException> { exceptionFun2() }
        validateEquals(expectedMessage2, actualException2.message, "message")
        validateEquals(true, actualException2 is IllegalEventException)
    }
}
