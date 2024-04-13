package app.emailsender.unittest

import app.emailsender.application.core.BaseEntityDTO
import app.emailsender.application.exceptions.BadRequestException
import app.emailsender.application.exceptions.InvalidOperationException
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.exceptions.RecordExistsException
import app.emailsender.domain.BaseEntity
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.USER_ID
import app.emailsender.utils.toDateTimeString
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal open class BaseTest {

    fun BaseEntity.setAuditFieldsForTest() {
        this.createdAt = CURRENT_DATE_TIME
        this.createdBy = USER_ID
        this.updatedAt = CURRENT_DATE_TIME
        this.updatedBy = USER_ID
    }

    fun BaseEntityDTO.updateDTOAuditFieldsForTest(item: BaseEntity) {
        this.createdAt = item.createdAt?.toDateTimeString()!!
        this.createdBy = item.createdBy!!
        this.updatedAt = item.updatedAt?.toDateTimeString()!!
        this.updatedBy = item.updatedBy!!
    }

    fun <T> validateNoRecordException(
        recordId: Any,
        className: String? = null,
        func: () -> T,
        message: String? = null
    ): Boolean {

        val exception = assertThrows<NoRecordException> {
            func.invoke()
        }

        val expectedMessage = message ?: "$className: No record exists for the provided identifier - $recordId"
        val actualMessage = exception.message
        validateEquals(expectedMessage, actualMessage, "exception message")

        return true
    }

    fun <T> validateBadRequestException(
        func: () -> T,
        message: String
    ): Boolean {

        val exception = assertThrows<BadRequestException> {
            func.invoke()
        }

        validateEquals(message, exception.message, "exception message")

        return true
    }

    fun <T> validateRecordExistsException(
        recordId: Any,
        className: String? = null,
        func: () -> T,
        message: String? = null
    ): Boolean {

        val exception = assertThrows<RecordExistsException> {
            func.invoke()
        }

        val expectedMessage = message ?: "$className: A record identified with - $recordId - exists"
        val actualMessage = exception.message
        validateEquals(expectedMessage, actualMessage, "exception message")

        return true
    }

    fun <T> validateInvalidOperationException(
        operation: Any? = null,
        className: String? = null,
        func: () -> T,
        message: String? = null
    ): Boolean {
        require(operation != null || message != null) {
            "Either operation or message must be provided"
        }

        val exception = assertThrows<InvalidOperationException> {
            func.invoke()
        }

        val expectedMessage = message ?: "$className: The operation - $operation - is not allowed"
        val actualMessage = exception.message
        validateEquals(expectedMessage, actualMessage, "exception message")

        return true
    }
}
