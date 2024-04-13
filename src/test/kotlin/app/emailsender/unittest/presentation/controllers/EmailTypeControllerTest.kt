package app.emailsender.unittest.presentation.controllers

import app.emailsender.application.core.DeleteRecordViewModel
import app.emailsender.application.core.emailtypes.commands.createemailtype.CreateEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.deleteemailtype.DeleteEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.updateemailtype.UpdateEmailTypeCommand
import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.queries.getemailtypes.GetEmailTypesQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypesViewModel
import app.emailsender.application.core.interfaces.CreateItemCommandHandler
import app.emailsender.application.core.interfaces.DeleteItemCommandHandler
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.core.interfaces.GetItemsQueryHandler
import app.emailsender.application.core.interfaces.UpdateItemCommandHandler
import app.emailsender.presentation.controllers.EmailTypeController
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.validateEmailTypeDTOFields
import app.emailsender.utils.validateEquals
import app.emailsender.utils.validateNotNull
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class EmailTypeControllerTest : ControllersTest() {
    @Mock
    private lateinit var getEmailTypeQueryHandler: GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>

    @Mock
    private lateinit var getEmailTypesQueryHandler: GetItemsQueryHandler<GetEmailTypesQuery, EmailTypesViewModel>

    @Mock
    private lateinit var createEmailTypeCommandHandler: CreateItemCommandHandler<CreateEmailTypeCommand, EmailTypeViewModel>

    @Mock
    private lateinit var updateEmailTypeCommandHandler: UpdateItemCommandHandler<UpdateEmailTypeCommand, EmailTypeViewModel>

    @Mock
    private lateinit var deleteEmailTypeCommandHandler: DeleteItemCommandHandler<DeleteEmailTypeCommand, DeleteRecordViewModel>

    @InjectMocks
    private lateinit var emailTypeController: EmailTypeController

    private val emailTypesTest = EmailTypesTest()
    private val emailType = emailTypesTest.getEmailType()
    private val emailTypeDTO = emailTypesTest.transFormToDTO(emailType)
    private val emailTypesVM = EmailTypesViewModel(listOf(emailTypeDTO))
    private val emailTypesVMMono = Mono.just(emailTypesVM)
    private val emailTypeVM = EmailTypeViewModel(emailTypeDTO)
    private val emailTypeVMMono = Mono.just(emailTypeVM)

    @Test
    fun testGetItems() {
        // Arrange
        whenever(getEmailTypesQueryHandler.getItems(argThat { true })).thenReturn(emailTypesVMMono)

        // Act
        val result = emailTypeController.getItems(userId, userScopes)

        // Assert
        StepVerifier.create(result)
            .expectNextMatches {
                val actualVM = validateItemsBaseViewModel(it)
                validateEquals(1, actualVM?.emailTypes?.size, "emailTypes.size")
                val actualDTO = actualVM?.emailTypes?.get(0)
                validateNotNull(actualDTO, "emailTypeDTO")
                actualDTO!!.validateEmailTypeDTOFields(emailType)
                true
            }
            .verifyComplete()
    }

    @Test
    fun testGetItem() {
        // Arrange
        whenever(getEmailTypeQueryHandler.getItem(argThat { id == emailType.id })).thenReturn(emailTypeVMMono)

        // Act
        val result = emailTypeController.getItem(emailType.id, userId, userScopes)

        // Assert
        StepVerifier.create(result)
            .expectNextMatches {
                val actualVM = validateItemDetailBaseViewModel(it)
                val actualDTO = actualVM?.emailType
                validateNotNull(actualDTO, "emailTypeDTO")
                actualDTO!!.validateEmailTypeDTOFields(emailType)
                true
            }
            .verifyComplete()
    }

    @Test
    fun testCreateItem() {
        // Arrange
        val command = CreateEmailTypeCommand(
            type = emailType.type,
            description = emailType.description!!,
        )

        whenever(createEmailTypeCommandHandler.createItem(eq(command), eq(userId))).thenReturn(emailTypeVMMono)

        // Act
        val result = emailTypeController.createItem(userId, userScopes, command)

        // Assert
        StepVerifier.create(result)
            .expectNextMatches {
                val actualVM = validateItemDetailBaseViewModel(it, HttpStatus.CREATED, "Item created successfully")
                val actualDTO = actualVM?.emailType
                validateNotNull(actualDTO, "emailTypeDTO")
                actualDTO!!.validateEmailTypeDTOFields(emailType)
                true
            }
            .verifyComplete()
    }

    @Test
    fun testUpdateItem() {
        // Arrange
        val command = UpdateEmailTypeCommand(
            id = emailType.id,
            type = emailType.type,
            description = emailType.description!!,
        )

        whenever(updateEmailTypeCommandHandler.updateItem(eq(command), eq(userId))).thenReturn(emailTypeVMMono)

        // Act
        val result = emailTypeController.updateItem(emailType.id, userId, userScopes, command)

        // Assert
        StepVerifier.create(result)
            .expectNextMatches {
                val actualVM = validateItemDetailBaseViewModel(it, HttpStatus.OK, "Item updated successfully")
                val actualDTO = actualVM?.emailType
                validateNotNull(actualDTO, "emailTypeDTO")
                actualDTO!!.validateEmailTypeDTOFields(emailType)
                true
            }
            .verifyComplete()
    }

    @Test
    fun testDeleteItem() {
        // Arrange
        val deleteVMMono = Mono.just(DeleteRecordViewModel())

        whenever(
            deleteEmailTypeCommandHandler.deleteItem(
                argThat { id == emailType.id },
                eq(userId)
            )
        ).thenReturn(deleteVMMono)

        // Act
        val result = emailTypeController.deleteItem(emailType.id, userId, userScopes)

        // Assert
        StepVerifier.create(result)
            .expectNextMatches {
                val actualVM = validateBaseViewModel(it, HttpStatus.OK)
                validateNotNull(actualVM, "deleteItemTypeVM")
                true
            }
            .verifyComplete()
    }

}
