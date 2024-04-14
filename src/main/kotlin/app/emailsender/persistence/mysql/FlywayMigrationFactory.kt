package app.emailsender.persistence.mysql

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class FlywayMigrationFactory(
    private val mysqlConfig: MysqlConfig,
    private val flywayConfig: FlywayConfig
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
            .dataSource(
                "jdbc:mysql://${mysqlConfig.host}:${mysqlConfig.port}/${mysqlConfig.database}",
                mysqlConfig.username,
                mysqlConfig.password
            )
            .locations(flywayConfig.locations)
            .baselineOnMigrate(flywayConfig.baselineOnMigrate.toBoolean())
            .load()
    }
}
