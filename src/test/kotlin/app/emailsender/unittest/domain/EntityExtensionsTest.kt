package app.emailsender.unittest.domain

import app.emailsender.domain.BaseEntity
import app.emailsender.domain.setAuditFields
import app.emailsender.domain.updateAuditFields
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.CURRENT_DATE_TIME
import app.emailsender.utils.USER_ID
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test

internal class EntityExtensionsTest : BaseTest() {

    class SampleEntity : BaseEntity()

    @Test
    fun testSetAuditFields() {
        // Arrange
        val testEntity = SampleEntity()

        // Act
        testEntity.setAuditFields(USER_ID, CURRENT_DATE_TIME)

        // Assert
        validateEquals(CURRENT_DATE_TIME, testEntity.createdAt, "createdAt")
        validateEquals(USER_ID, testEntity.createdBy, "createdBy")
        validateEquals(CURRENT_DATE_TIME, testEntity.updatedAt, "updatedAt")
        validateEquals(USER_ID, testEntity.updatedBy, "updatedBy")
    }

    @Test
    fun testUpdateAuditFields() {
        // Arrange
        val testEntity = SampleEntity()

        // Act
        testEntity.updateAuditFields(USER_ID, CURRENT_DATE_TIME)

        // Assert
        validateEquals(CURRENT_DATE_TIME, testEntity.updatedAt)
        validateEquals(USER_ID, testEntity.updatedBy)
    }

}
