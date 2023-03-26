package app.emailsender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmailsenderApplication

fun main(args: Array<String>) {
	runApplication<EmailsenderApplication>(*args)
}
