package app.emailsender.persistence.mysql.repositories

import app.emailsender.domain.emailtypes.EmailType
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailTypeRepository : ReactiveCrudRepository<EmailType, Int?>