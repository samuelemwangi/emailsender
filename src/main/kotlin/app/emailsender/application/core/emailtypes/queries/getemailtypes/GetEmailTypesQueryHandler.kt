package app.emailsender.application.core.emailtypes.queries.getemailtypes

import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypesViewModel
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.core.interfaces.GetItemsQueryHandler
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetEmailTypesQueryHandler(
    private val emailTypeRepository: EmailTypeRepository,
    private val getEmailTypeHelper: GetItemDTOHelper<EmailType, EmailTypeDTO>
) : GetItemsQueryHandler<GetEmailTypesQuery, EmailTypesViewModel> {

    override fun getItems(query: GetEmailTypesQuery): Mono<EmailTypesViewModel> {
        return emailTypeRepository.findAll()
            .map { getEmailTypeHelper.toDTO(it) }
            .collectList()
            .map { toViewModel(it) }
    }

    private fun toViewModel(emailTypes: List<EmailTypeDTO>): EmailTypesViewModel {
        return EmailTypesViewModel(emailTypes)
    }
}