package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.DatabaseOperationException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DatabaseOperationExceptionTest : BaseTest() {
    @Test
    fun testThrowDatabaseOperationException() {
        // Arrange
        val expectedMessage1 = "Database Operation Exception"
        val expectedException1 = DatabaseOperationException(expectedMessage1)

        val className = "Test"
        val operation = "Update"
        val expectedMessage2 = "$className: An error occurred performing action - $operation"
        val expectedException2 = DatabaseOperationException(operation, className)

        // Act
        val exceptionFun1: () -> Unit = { throw expectedException1 }
        val exceptionFun2: () -> Unit = { throw expectedException2 }

        // Assert
        val actualException1 = assertThrows<RuntimeException> { exceptionFun1() }
        validateEquals(expectedMessage1, actualException1.message, "message")
        validateEquals(true, actualException1 is DatabaseOperationException)

        val actualException2 = assertThrows<RuntimeException> { exceptionFun2() }
        validateEquals(expectedMessage2, actualException2.message, "message")
        validateEquals(true, actualException2 is DatabaseOperationException)
    }
}
