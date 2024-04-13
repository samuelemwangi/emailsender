package app.emailsender.unittest.application.core.emailtypes.queries

import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQueryHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.CURRENT_DATE_TIME_STRING
import app.emailsender.utils.randomInt
import app.emailsender.utils.validateBaseVMFields
import app.emailsender.utils.validateEmailTypeDTOFields
import app.emailsender.utils.validateItemDetailBaseVMFields
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class GetEmailTypeQueryHandlerTest : EmailTypesTest() {
    @Mock
    private lateinit var emailTypeRepository: EmailTypeRepository

    @Mock
    private lateinit var dateTimeHelper: DateTimeHelper

    @InjectMocks
    private lateinit var getEmailTypeQueryHandler: GetEmailTypeQueryHandler

    @Test
    fun testNonExistingEmailTypeItemThrowsNoRecordException() {
        // Arrange
        val targetId = randomInt()

        whenever(emailTypeRepository.findById(targetId)).thenReturn(Mono.empty())

        // Act &  Assert
        validateNoRecordException(
            recordId = targetId,
            className = EntityTypes.EMAIL_TYPE.labelText,
            func = { getEmailTypeQueryHandler.getItem(GetEmailTypeQuery(targetId)).block() }
        )
    }

    @Test
    fun testGetExistingEmailTypeItemReturnsValidDetails() {
        // Arrange
        val emailType = getEmailType()
        val query = GetEmailTypeQuery(emailType.id)

        whenever(emailTypeRepository.findById(query.id)).thenReturn(Mono.just(emailType))
        whenever(dateTimeHelper.resolveDate(CURRENT_DATE_TIME)).thenReturn(CURRENT_DATE_TIME_STRING)

        // Act & Assert
        StepVerifier.create(getEmailTypeQueryHandler.getItem(query))
            .expectNextMatches {
                it.validateBaseVMFields()
                it.validateItemDetailBaseVMFields()
                it.emailType.validateEmailTypeDTOFields(emailType)
            }
            .verifyComplete()
    }
}
