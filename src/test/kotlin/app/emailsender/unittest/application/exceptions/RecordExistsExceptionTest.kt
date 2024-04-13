package app.emailsender.unittest.application.exceptions

import app.emailsender.application.exceptions.RecordExistsException
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class RecordExistsExceptionTest : BaseTest() {
    @Test
    fun testThrowRecordExistsException() {
        // Arrange
        val expectedMessage1 = "Record Exists Exception"
        val expectedException1 = RecordExistsException(expectedMessage1)

        val className = "Test"
        val id = "123"
        val expectedMessage2 = "$className: A record identified with - $id - exists"
        val expectedException2 = RecordExistsException(id, className)

        // Act
        val exceptionFun1: () -> Unit = { throw expectedException1 }
        val exceptionFun2: () -> Unit = { throw expectedException2 }

        // Assert
        val actualException1 = assertThrows<RuntimeException> { exceptionFun1() }
        validateEquals(expectedMessage1, actualException1.message, "message")
        validateEquals(true, actualException1 is RecordExistsException)

        val actualException2 = assertThrows<RuntimeException> { exceptionFun2() }
        validateEquals(expectedMessage2, actualException2.message, "message")
        validateEquals(true, actualException2 is RecordExistsException)
    }
}
