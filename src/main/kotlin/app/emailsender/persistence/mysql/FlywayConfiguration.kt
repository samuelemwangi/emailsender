package app.emailsender.persistence.mysql

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(private val mysqlConfig: MysqlConfig) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
            .dataSource(
                "jdbc:mysql://${mysqlConfig.host}:${mysqlConfig.port}/${mysqlConfig.database}",
                mysqlConfig.username,
                mysqlConfig.password
            )
            .load()
    }
}
