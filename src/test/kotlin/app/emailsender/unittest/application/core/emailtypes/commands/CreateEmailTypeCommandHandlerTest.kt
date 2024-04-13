package app.emailsender.unittest.application.core.emailtypes.commands

import app.emailsender.application.core.emailtypes.commands.createemailtype.CreateEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.createemailtype.CreateEmailTypeCommandHandler
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.USER_ID
import app.emailsender.utils.randomSentence
import app.emailsender.utils.validateBaseVMFields
import app.emailsender.utils.validateEmailTypeDTOFields
import app.emailsender.utils.validateItemDetailBaseVMFields
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.argThat
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class CreateEmailTypeCommandHandlerTest : EmailTypesTest() {
    @Mock
    private lateinit var emailTypeRepository: EmailTypeRepository

    @Mock
    private lateinit var dateTimeHelper: DateTimeHelper

    @Mock
    private lateinit var getEmailTypeDTOHelper: GetItemDTOHelper<EmailType, EmailTypeDTO>

    @InjectMocks
    private lateinit var createEmailTypeCommandHandler: CreateEmailTypeCommandHandler

    @Test
    fun testCreateEmailTypeItemWithExistingTypeThrowsRecordExistsException() {
        // Arrange
        val emailType = getEmailType()
        val command = getCreateEmailTyeCommand(emailType.type, emailType.description)

        whenever(emailTypeRepository.findByType(command.type)).thenReturn(Mono.just(emailType))

        // Act & Assert
        validateRecordExistsException(
            recordId = command.type,
            className = EntityTypes.EMAIL_TYPE.labelText,
            func = { createEmailTypeCommandHandler.createItem(command, emailType.createdBy).block() }
        )
    }

    @Test
    fun testCreateValidEmailTypeItemReturnsCreatedEmailType() {
        // Arrange
        val emailType = getEmailType()
        val emailTypeDTO = transFormToDTO(emailType)
        val command = getCreateEmailTyeCommand(emailType.type, emailType.description)

        whenever(emailTypeRepository.findByType(command.type)).thenReturn(Mono.empty())
        whenever(dateTimeHelper.getCurrentDateTime()).thenReturn(CURRENT_DATE_TIME)
        whenever(
            emailTypeRepository.save(
                argThat { arg ->
                    arg.type == emailType.type &&
                            arg.description == emailType.description

                })
        ).thenReturn(
            Mono.just(emailType)
        )
        whenever(getEmailTypeDTOHelper.toDTO(emailType)).thenReturn(emailTypeDTO)

        // Act & Assert
        StepVerifier.create(createEmailTypeCommandHandler.createItem(command, USER_ID))
            .expectNextMatches {
                it.validateBaseVMFields()
                it.validateItemDetailBaseVMFields()
                it.emailType.validateEmailTypeDTOFields(emailType)
            }
            .verifyComplete()
    }

    private fun getCreateEmailTyeCommand(type: String? = null, desc: String? = null) = CreateEmailTypeCommand(
        type = type ?: randomSentence(1, true),
        description = desc ?: randomSentence(3, true)
    )
}
