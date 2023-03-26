package app.emailsender.application.interfaces

import java.time.LocalDateTime

interface DateTimeHelper {
    fun getCurrentDateTime(): LocalDateTime
    fun getTimeStamp(): String
    fun resolveDate(dateTime: LocalDateTime?): String
    fun toUnixEpochDate(dateTime: LocalDateTime): Long
}