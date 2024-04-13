package app.emailsender.persistence.mysql

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "db.mysql")
class MysqlConfig {
    lateinit var host: String
    lateinit var port: String
    lateinit var database: String
    lateinit var username: String
    lateinit var password: String
    lateinit var serverZone: String
}
