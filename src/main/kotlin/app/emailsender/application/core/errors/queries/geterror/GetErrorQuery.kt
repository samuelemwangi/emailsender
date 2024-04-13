package app.emailsender.application.core.errors.queries.geterror

import app.emailsender.application.BaseQuery
import org.springframework.http.HttpStatusCode

class GetErrorQuery(
    val errorMessage: String,
    val httpStatus: HttpStatusCode
): BaseQuery()
