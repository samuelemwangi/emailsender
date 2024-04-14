package app.emailsender.persistence.mysql

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "flyway")
class FlywayConfig {
    lateinit var locations: String
    lateinit var baselineOnMigrate: String
}
