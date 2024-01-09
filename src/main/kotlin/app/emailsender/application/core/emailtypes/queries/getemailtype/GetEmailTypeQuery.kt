package app.emailsender.application.core.emailtypes.queries.getemailtype

import app.emailsender.application.BaseQuery

data class GetEmailTypeQuery(
    val id: Int
) : BaseQuery()
