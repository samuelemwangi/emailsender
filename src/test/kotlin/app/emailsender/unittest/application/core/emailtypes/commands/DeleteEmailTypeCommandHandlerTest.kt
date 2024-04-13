package app.emailsender.unittest.application.core.emailtypes.commands

import app.emailsender.application.core.emailtypes.commands.deleteemailtype.DeleteEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.deleteemailtype.DeleteEmailTypeCommandHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.USER_ID
import app.emailsender.utils.randomInt
import app.emailsender.utils.randomUUID
import app.emailsender.utils.validateBaseVMFields
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class DeleteEmailTypeCommandHandlerTest : EmailTypesTest() {
    @Mock
    private lateinit var emailTypeRepository: EmailTypeRepository

    @Mock
    private lateinit var dateTimeHelper: DateTimeHelper

    @InjectMocks
    private lateinit var deleteEmailTypeCommandHandler: DeleteEmailTypeCommandHandler

    @Test
    fun testDeleteNonExistentEmailTypeItemThrowsNoRecordException() {
        // Arrange
        val command = DeleteEmailTypeCommand(id = randomInt())

        whenever(emailTypeRepository.findById(command.id)).thenReturn(Mono.empty())

        // Act & Assert
        validateNoRecordException(
            recordId = command.id,
            className = EntityTypes.EMAIL_TYPE.labelText,
            func = { deleteEmailTypeCommandHandler.deleteItem(command, USER_ID).block() }
        )
    }

    @Test
    fun testDeleteExistingEmailTypeDeletesEmailTypeItem() {
        // Arrange
        val emailType = getEmailType()

        val updateTime = CURRENT_DATE_TIME.plusSeconds(2)
        val userId = randomUUID()

        val updatedEmailType = emailType.copy().also {
            it.type = emailType.type
            it.description = emailType.description!!
            it.createdBy = USER_ID
            it.createdAt = CURRENT_DATE_TIME
            it.updatedBy = userId
            it.updatedAt = updateTime
            it.isDeleted = true
        }

        val command = DeleteEmailTypeCommand(id = emailType.id)

        whenever(emailTypeRepository.findById(command.id)).thenReturn(Mono.just(emailType))
        whenever(dateTimeHelper.getCurrentDateTime()).thenReturn(updateTime)
        whenever(
            emailTypeRepository.save(
                argThat {
                    it.id == command.id &&
                            it.type == emailType.type &&
                            it.description == emailType.description
                })
        ).thenReturn(Mono.just(updatedEmailType))


        // Act & Assert
        StepVerifier.create(deleteEmailTypeCommandHandler.deleteItem(command, userId))
            .expectNextMatches {
                it.validateBaseVMFields()
                true
            }
            .verifyComplete()
    }
}
