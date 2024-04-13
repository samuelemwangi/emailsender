package app.emailsender.unittest.persistence.mysql

import app.emailsender.persistence.mysql.MysqlConfig
import app.emailsender.persistence.mysql.MysqlConnectionConfigFactory
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.validateNotNull
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.whenever

internal class MysqlConnectionConfigFactoryTest : BaseTest() {

    @Mock
    private lateinit var mysqlConfig: MysqlConfig

    @Mock
    private lateinit var connectionFactory: ConnectionFactory

    @InjectMocks
    private lateinit var mysqlConnectionConfigFactory: MysqlConnectionConfigFactory

    @Test
    fun testConnectionFactory() {
        // Arrange
        whenever(mysqlConfig.host).thenReturn("localhost")
        whenever(mysqlConfig.port).thenReturn("3306")
        whenever(mysqlConfig.username).thenReturn("root")
        whenever(mysqlConfig.password).thenReturn("password")
        whenever(mysqlConfig.database).thenReturn("test")
        whenever(mysqlConfig.serverZone).thenReturn("UTC")

        // Act
        val mysqlConnectionFactory = mysqlConnectionConfigFactory.connectionFactory()

        // Assert
        validateNotNull(mysqlConnectionFactory)
    }

    @Test
    fun testMysqlEntityTemplate() {
        // Act
        val entityTemplate = mysqlConnectionConfigFactory.mysqlEntityTemplate(connectionFactory)

        // Assert
        validateNotNull(entityTemplate)
        validateNotNull(entityTemplate.databaseClient)
        validateNotNull(entityTemplate.databaseClient.connectionFactory)
        validateNotNull(entityTemplate.converter)
    }

    @Test
    fun testInitializer() {
        // Act
        val initializer = mysqlConnectionConfigFactory.initializer(connectionFactory)

        // Assert
        validateNotNull(initializer)
    }
}
