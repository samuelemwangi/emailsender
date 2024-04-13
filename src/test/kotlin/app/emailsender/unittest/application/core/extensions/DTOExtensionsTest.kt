package app.emailsender.unittest.application.core.extensions

import app.emailsender.application.core.BaseEntityDTO
import app.emailsender.application.core.extensions.recordOwnedByCurrentUser
import app.emailsender.application.core.extensions.setDTOAuditFields
import app.emailsender.domain.BaseEntity
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.randomUUID
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DTOExtensionsTest : BaseTest() {

    class TestEntity : BaseEntity()
    class TestEntityDTO : BaseEntityDTO()

    @Test
    fun testSetDTOAuditFields() {
        // Arrange
        val now = LocalDateTime.now()
        val userId = randomUUID()
        val userId2 = randomUUID()

        val entity = TestEntity()
        entity.createdAt = now
        entity.createdBy = userId
        entity.updatedAt = now
        entity.updatedBy = userId2

        val dto = TestEntityDTO()
        val dateResolver: (LocalDateTime?) -> String = { it.toString() }

        // Act & Assert
        dto.setDTOAuditFields(entity, dateResolver).also {
            validateEquals(now.toString(), dto.createdAt, "createdAt")
            validateEquals(userId, dto.createdBy, "createdBy")
            validateEquals(now.toString(), dto.updatedAt, "updatedAt")
            validateEquals(userId2, dto.updatedBy, "updatedBy")
        }
    }

    @Test
    fun testRecordOwnedByCurrentUser() {
        // Arrange
        val now = LocalDateTime.now().toString()
        val userId = randomUUID()

        val entityDTO = TestEntityDTO()
        entityDTO.createdAt = now
        entityDTO.createdBy = userId
        entityDTO.updatedAt = now
        entityDTO.updatedBy = userId

        val userId2 = randomUUID()
        val entityDTO2 = TestEntityDTO()
        entityDTO2.createdAt = now
        entityDTO2.createdBy = userId2
        entityDTO2.updatedAt = now
        entityDTO2.updatedBy = userId2

        // Act
        val result = entityDTO.recordOwnedByCurrentUser(userId)
        val result2 = entityDTO2.recordOwnedByCurrentUser(userId)

        // Assert
        validateEquals(true, result)
        validateEquals(false, result2)
    }
}
