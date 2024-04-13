package app.emailsender.persistence.mysql.repositories

import app.emailsender.domain.emailtypes.EmailType
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface EmailTypeRepository : ReactiveCrudRepository<EmailType, Int> {
    @Query("SELECT * FROM email_types WHERE is_deleted = 0")
    override fun findAll(): Flux<EmailType>

    @Query("SELECT * FROM email_types WHERE type = :type AND is_deleted = 0")
    fun findByType(type: String): Mono<EmailType>

    @Query("SELECT * FROM email_types WHERE id = :id AND is_deleted = 0")
    override fun findById(id: Int): Mono<EmailType>
}
