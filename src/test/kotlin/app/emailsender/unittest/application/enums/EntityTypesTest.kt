package app.emailsender.unittest.application.enums

import app.emailsender.application.enums.EntityTypes
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateEquals
import org.junit.jupiter.api.Test

internal class EntityTypesTest : BaseTest() {
    @Test
    fun testEntityTypes() {
        // Arrange
        val emailType = EntityTypes.EMAIL_TYPE

        // Act & Assert
        validateEquals("emailtype", emailType.labelText, "emailType.labelText")
    }
}
