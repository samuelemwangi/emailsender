package app.emailsender.infrastructure.utilities

import app.emailsender.application.interfaces.DateTimeHelper
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class DateTimeHelperService(
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")
) : DateTimeHelper {
    override fun getCurrentDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    override fun getTimeStamp(): String {
        return getCurrentDateTime().toString()
    }

    override fun resolveDate(dateTime: LocalDateTime?): String {
        return try {
            dateTimeFormatter.format(dateTime)
        } catch (e: Exception) {
            ""
        }
    }

    override fun toUnixEpochDate(dateTime: LocalDateTime): Long {
        return dateTime.toEpochSecond(ZoneOffset.MAX)
    }
}
