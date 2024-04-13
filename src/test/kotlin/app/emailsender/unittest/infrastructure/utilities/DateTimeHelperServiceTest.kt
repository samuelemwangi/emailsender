package app.emailsender.unittest.infrastructure.utilities

import app.emailsender.infrastructure.utilities.DateTimeHelperService
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DateTimeHelperServiceTest : BaseTest() {

    @Test
    fun testGetTimeStamp() {
        // Arrange
        val dateTimeHelperService = DateTimeHelperService()
        val currDateTime = LocalDateTime.now()

        // Act
        val result = dateTimeHelperService.getTimeStamp()

        // Assert
        validateEquals(currDateTime.year, result.substring(0, 4).toInt(), "year")
        validateEquals(currDateTime.monthValue, result.substring(5, 7).toInt(), "month")
        validateEquals(currDateTime.dayOfMonth, result.substring(8, 10).toInt(), "day")
    }

    @Test
    fun testResolveDate() {
        // Arrange
        val dateTimeHelperService = DateTimeHelperService()
        val currDateTime = LocalDateTime.now()

        // Act
        val result = dateTimeHelperService.resolveDate(currDateTime)

        // Assert
        validateEquals(true, result.uppercase().contains(currDateTime.dayOfWeek.toString().uppercase() + ","))
        validateEquals(true, result.contains(currDateTime.dayOfMonth.toString()))
        validateEquals(true, result.uppercase().contains(currDateTime.month.toString().uppercase()))
    }

    @Test
    fun testToUnixEpochDate() {
        // Arrange
        val dateTimeHelperService = DateTimeHelperService()
        val currDateTime = LocalDateTime.now()

        // Act
        val result = dateTimeHelperService.toUnixEpochDate(currDateTime)

        // Assert
        validateEquals(true, result > 0)
    }
}
