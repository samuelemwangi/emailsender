package app.emailsender.application.core.emailtypes.queries.getemailtype

import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.interfaces.GetItemHelper
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class GetEmailTypeQueryHandler(
    private val emailTypeRepository: EmailTypeRepository
) : GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>, GetItemHelper<EmailType, EmailTypeDTO> {

    override fun getItem(query: GetEmailTypeQuery): Mono<EmailTypeViewModel> {
        return emailTypeRepository.findById(query.id)
            .map { toDto(it) }
            .map { toViewModel(it) }
    }

    override fun toDto(entity: EmailType): EmailTypeDTO {

        val emailType = EmailTypeDTO(
            id = entity.id,
            type = entity.type,
            description = entity.description,
        )

        emailType.createdBy = emailType.createdBy
        emailType.createdDate = emailType.createdDate
        emailType.lastModifiedBy = emailType.lastModifiedBy
        emailType.lastModifiedDate = emailType.lastModifiedDate

        return emailType
    }

    private fun toViewModel(dto: EmailTypeDTO): EmailTypeViewModel {
        return EmailTypeViewModel(dto)
    }
}