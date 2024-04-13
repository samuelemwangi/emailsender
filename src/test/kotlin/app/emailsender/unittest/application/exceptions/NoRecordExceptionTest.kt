package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NoRecordExceptionTest : BaseTest() {
    @Test
    fun testThrowNoRecordException() {
        // Arrange
        val expectedMessage1 = "No Record Exception"
        val expectedException1 = NoRecordException(expectedMessage1)

        val className = "Test"
        val id = "123"
        val expectedMessage2 = "$className: No record exists for the provided identifier - $id"
        val expectedException2 = NoRecordException(id, className)

        // Act
        val exceptionFun1: () -> Unit = { throw expectedException1 }
        val exceptionFun2: () -> Unit = { throw expectedException2 }

        // Assert
        val actualException1 = assertThrows<RuntimeException> { exceptionFun1() }
        validateEquals(expectedMessage1, actualException1.message, "message")
        validateEquals(true, actualException1 is NoRecordException)

        val actualException2 = assertThrows<RuntimeException> { exceptionFun2() }
        validateEquals(expectedMessage2, actualException2.message, "message")
        validateEquals(true, actualException2 is NoRecordException)
    }
}
