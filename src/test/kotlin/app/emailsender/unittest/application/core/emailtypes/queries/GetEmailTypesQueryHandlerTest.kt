package app.emailsender.unittest.application.core.emailtypes.queries

import app.emailsender.application.core.emailtypes.queries.getemailtypes.GetEmailTypesQuery
import app.emailsender.application.core.emailtypes.queries.getemailtypes.GetEmailTypesQueryHandler
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import app.emailsender.unittest.application.core.emailtypes.EmailTypesTest
import app.emailsender.utils.validateBaseVMFields
import app.emailsender.utils.validateEmailTypeDTOFields
import app.emailsender.utils.validateEquals
import app.emailsender.utils.validateItemsBaseViewModelFields
import app.emailsender.utils.validateNotNull
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

internal class GetEmailTypesQueryHandlerTest : EmailTypesTest() {
    @Mock
    private lateinit var emailTypeRepository: EmailTypeRepository

    @Mock
    private lateinit var getEmailTypeDTOHelper: GetItemDTOHelper<EmailType, EmailTypeDTO>

    @InjectMocks
    private lateinit var getEmailTypesQueryHandler: GetEmailTypesQueryHandler

    @Test
    fun testGetNonExistingEmailTypeItemsReturnsEmptyList() {
        // Arrange
        val query = GetEmailTypesQuery()

        whenever(emailTypeRepository.findAll()).thenReturn(Flux.empty())

        // Act & Assert
        StepVerifier.create(getEmailTypesQueryHandler.getItems(query))
            .expectNextMatches {
                it.validateBaseVMFields()
                it.validateItemsBaseViewModelFields()
                validateNotNull(it.emailTypes, "emailTypes")
                validateEquals(0, it.emailTypes.size, "size")
                true
            }
            .verifyComplete()
    }

    @Test
    fun testGetExistingEmailTypeItemsReturnsValidDetails() {
        // Arrange
        val emailTypes = listOf(getEmailType(), getEmailType())
        val query = GetEmailTypesQuery()

        whenever(emailTypeRepository.findAll()).thenReturn(Flux.fromIterable(emailTypes))
        whenever(getEmailTypeDTOHelper.toDTO(any())).then { transFormToDTO(it.arguments[0] as EmailType) }

        // Act & Assert
        StepVerifier.create(getEmailTypesQueryHandler.getItems(query))
            .expectNextMatches {
                it.validateBaseVMFields()
                it.validateItemsBaseViewModelFields()
                validateNotNull(it.emailTypes, "emailTypes")
                it.emailTypes.forEachIndexed { index, emailTypeDTO ->
                    emailTypeDTO.validateEmailTypeDTOFields(emailTypes[index])
                }
                true
            }
            .verifyComplete()
    }
}
