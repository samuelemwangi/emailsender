package app.emailsender.unittest.presentation.exceptions

import app.emailsender.application.core.errors.queries.geterror.GetErrorQuery
import app.emailsender.application.core.errors.viewmodels.ErrorDTO
import app.emailsender.application.core.errors.viewmodels.ErrorViewModel
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.exceptions.DatabaseOperationException
import app.emailsender.application.exceptions.InvalidOperationException
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.exceptions.RecordExistsException
import app.emailsender.presentation.exceptions.RestExceptionHandler
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.CURRENT_DATE_TIME_STRING
import app.emailsender.utils.validateEquals
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.mock.http.server.reactive.MockServerHttpResponse
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono

internal class RestExceptionHandlerTest : BaseTest() {
    @Mock
    private lateinit var getErrorQueryHandler: GetItemQueryHandler<GetErrorQuery, ErrorViewModel>

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var serverWebExchange: ServerWebExchange

    @InjectMocks
    private lateinit var restExceptionHandler: RestExceptionHandler

    @Test
    fun testHandle() {
        // Arrange
        val exceptions = listOf(
            Pair(RecordExistsException("record exists"), HttpStatus.CONFLICT),
            Pair(NoRecordException("no record exists"), HttpStatus.NOT_FOUND),
            Pair(InvalidOperationException("invalid operation"), HttpStatus.NOT_ACCEPTABLE),
            Pair(ServerWebInputException("server web input"), HttpStatus.BAD_REQUEST),
            Pair(MethodNotAllowedException(HttpMethod.HEAD, listOf(HttpMethod.GET)), HttpStatus.METHOD_NOT_ALLOWED),
            Pair(ResponseStatusException(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST),
            Pair(DatabaseOperationException("database operation exception"), HttpStatus.INTERNAL_SERVER_ERROR),
            Pair(Exception("test error message"), HttpStatus.INTERNAL_SERVER_ERROR),
        )
        exceptions.forEach { exception ->
            val responseErrorMessage = exception.first.message ?: "test message"
            val responseStatusCode = exception.second
            val responseTimeStamp = CURRENT_DATE_TIME_STRING

            val errorDTO = ErrorDTO(
                message = responseErrorMessage,
                timestamp = responseTimeStamp
            )
            val errorViewModel = ErrorViewModel(errorDTO)

            val response = MockServerHttpResponse()
            whenever(serverWebExchange.response).thenReturn(response)
            whenever(getErrorQueryHandler.getItem(any())).thenReturn(Mono.just(errorViewModel))
            val targetBytes = errorViewModel.toString().toByteArray()

            whenever(objectMapper.writeValueAsBytes(any())).thenReturn(targetBytes)

            //Act
            restExceptionHandler.handle(serverWebExchange, exception.first).block()

            //Assert
            validateEquals(responseStatusCode, response.statusCode, "statusCode")
            validateEquals("application/json", response.headers.contentType.toString(), "contentType")
        }
    }
}
