package app.emailsender.utils

internal val USER_ID by lazy { randomUUID() }

internal val CURRENT_DATE_TIME by lazy { randomDateTime() }

internal val CURRENT_DATE_TIME_STRING by lazy { CURRENT_DATE_TIME.toDateTimeString() }
