package app.emailsender

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
@OpenAPIDefinition
class EmailsenderApplication

fun main(args: Array<String>) {
    runApplication<EmailsenderApplication>(*args)
}
