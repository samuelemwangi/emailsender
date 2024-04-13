package app.emailsender.unittest.application.core.emailtypes.commands

import app.emailsender.application.core.emailtypes.commands.updateemailtype.UpdateEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.updateemailtype.UpdateEmailTypeCommandHandler
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.USER_ID
import app.emailsender.utils.randomInt
import app.emailsender.utils.randomSentence
import app.emailsender.utils.randomUUID
import app.emailsender.utils.validateBaseVMFields
import app.emailsender.utils.validateEmailTypeDTOFields
import app.emailsender.utils.validateItemDetailBaseVMFields
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class UpdateEmailTypeCommandHandlerTest : EmailTypesTest() {
    @Mock
    private lateinit var emailTypeRepository: EmailTypeRepository

    @Mock
    private lateinit var dateTimeHelper: DateTimeHelper

    @Mock
    private lateinit var getEmailTypeDTOHelper: GetItemDTOHelper<EmailType, EmailTypeDTO>

    @InjectMocks
    private lateinit var updateEmailTypeCommandHandler: UpdateEmailTypeCommandHandler

    @Test
    fun testUpdateEmailTypeItemWithInvalidIdThrowsBadRequestException() {
        // Arrange
        val command = getUpdateEmailTyeCommand().also {
            it.id = null
        }

        // Act & Assert
        validateBadRequestException(
            message = "Kindly provide a valid emailtype Id",
            func = { updateEmailTypeCommandHandler.updateItem(command, USER_ID).block() }
        )
    }

    @Test
    fun testUpdateNonExistentEmailTypeItemThrowsNoRecordException() {
        // Arrange
        val command = getUpdateEmailTyeCommand()

        whenever(emailTypeRepository.findById(command.id!!)).thenReturn(Mono.empty())

        // Act & Assert
        validateNoRecordException(
            recordId = command.id!!,
            className = EntityTypes.EMAIL_TYPE.labelText,
            func = { updateEmailTypeCommandHandler.updateItem(command, USER_ID).block() }
        )
    }

    @Test
    fun testUpdateEmailTypeItemWithValidDetailsUpdatesEmailTypeItem() {
        // Arrange
        val emailType = getEmailType()
        val command = getUpdateEmailTyeCommand(emailType.id)

        val updateTime = CURRENT_DATE_TIME.plusSeconds(2)
        val userId = randomUUID()

        val updatedEmailType = emailType.copy().also {
            it.type = command.type!!
            it.description = command.description!!
            it.createdBy = USER_ID
            it.createdAt = CURRENT_DATE_TIME
            it.updatedBy = userId
            it.updatedAt = updateTime
        }
        val updatedEmailTypeDTO = transFormToDTO(updatedEmailType)

        whenever(emailTypeRepository.findById(command.id!!)).thenReturn(Mono.just(emailType))
        whenever(dateTimeHelper.getCurrentDateTime()).thenReturn(updateTime)
        whenever(
            emailTypeRepository.save(
                argThat {
                    it.id == command.id &&
                            it.type == command.type &&
                            it.description == command.description
                })
        ).thenReturn(Mono.just(updatedEmailType))

        whenever(getEmailTypeDTOHelper.toDTO(updatedEmailType)).thenReturn(updatedEmailTypeDTO)

        // Act & Assert
        StepVerifier.create(updateEmailTypeCommandHandler.updateItem(command, userId))
            .expectNextMatches {
                it.validateBaseVMFields()
                it.validateItemDetailBaseVMFields()
                it.emailType.validateEmailTypeDTOFields(updatedEmailType)
            }
            .verifyComplete()
    }

    private fun getUpdateEmailTyeCommand(
        id: Int? = null,
        type: String? = null,
        desc: String? = null
    ) =
        UpdateEmailTypeCommand(
            id = id ?: randomInt(),
            type = type ?: randomSentence(1, true),
            description = desc ?: randomSentence(3, true)
        )
}
