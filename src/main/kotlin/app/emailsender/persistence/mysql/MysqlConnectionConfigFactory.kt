package app.emailsender.persistence.mysql

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.dialect.MySqlDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import java.time.ZoneId


@Component
@EnableR2dbcRepositories(
    entityOperationsRef = "mysqlEntityTemplate",
    basePackages = ["app.emailsender.persistence.mysql.repositories"]
)
class MysqlConnectionConfigFactory(private val mysqlConfig: MysqlConfig) : AbstractR2dbcConfiguration() {

    @Bean("mysqlConnectionFactory")
    override fun connectionFactory(): ConnectionFactory {
        return MySqlConnectionFactory.from(
            MySqlConnectionConfiguration
                .builder()
                .host(mysqlConfig.host)
                .port(mysqlConfig.port.toInt())
                .username(mysqlConfig.username)
                .password(mysqlConfig.password)
                .database(mysqlConfig.database)
                .serverZoneId(ZoneId.of(mysqlConfig.serverZone))
                .build()
        );
    }

    @Bean
    fun mysqlEntityTemplate(@Qualifier("mysqlConnectionFactory") connectionFactory: ConnectionFactory): R2dbcEntityOperations {
        val dataAccessStrategy = DefaultReactiveDataAccessStrategy(MySqlDialect.INSTANCE)
        val databaseClient = DatabaseClient.builder()
            .connectionFactory(connectionFactory)
            .bindMarkers(MySqlDialect.INSTANCE.bindMarkersFactory)
            .build()
        return R2dbcEntityTemplate(databaseClient, dataAccessStrategy)
    }

    @Bean
    fun initializer(@Qualifier("mysqlConnectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(databasePopulator())
        return initializer
    }

    private fun databasePopulator(): CompositeDatabasePopulator {
        val populator = CompositeDatabasePopulator()
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("db/tables_schema.sql")))
        return populator
    }
}