package app.emailsender.application.core.errors.queries.geterror

import org.springframework.http.HttpStatus

class GetErrorQuery(
    val errorMessage: String,
    val httpStatus: HttpStatus
)