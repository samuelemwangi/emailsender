package app.emailsender.application.core.emailtypes.queries.getemailtype

import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.extensions.setDtoAuditFields
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class GetEmailTypeQueryHandler(
    private val emailTypeRepository: EmailTypeRepository,
    private val dateTimeHelper: DateTimeHelper
) : GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>, GetItemDTOHelper<EmailType, EmailTypeDTO> {

    override fun getItem(query: GetEmailTypeQuery): Mono<EmailTypeViewModel> {
        return emailTypeRepository.findById(query.id)
            .switchIfEmpty(Mono.error(NoRecordException("${query.id}", EntityTypes.EMAIL_TYPE.labelText)))
            .map { toDTO(it) }
            .map { toViewModel(it) }
    }

    override fun toDTO(entity: EmailType): EmailTypeDTO {
        return EmailTypeDTO(
            id = entity.id,
            type = entity.type,
            description = entity.description,
        ).also {
            it.setDtoAuditFields(entity, dateTimeHelper::resolveDate)
        }
    }

    private fun toViewModel(dto: EmailTypeDTO): EmailTypeViewModel {
        return EmailTypeViewModel(dto)
    }
}
