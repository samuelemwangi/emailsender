package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.interfaces.CreateItemCommandHandler
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.exceptions.RecordExistsException
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.domain.setAuditFields
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateEmailTypeCommandHandler(
    private val emailTypeRepository: EmailTypeRepository,
    private val dateTimeHelper: DateTimeHelper,
    private val getEmailTypeDTOHelper: GetItemDTOHelper<EmailType, EmailTypeDTO>
) : CreateItemCommandHandler<CreateEmailTypeCommand, EmailTypeViewModel> {

    override fun createItem(command: CreateEmailTypeCommand, userId: String?): Mono<EmailTypeViewModel> {

        emailTypeRepository.findByType(command.type).toFuture().get()?.let {
            throw RecordExistsException(command.type, EntityTypes.EMAIL_TYPE.labelText)
        }

        val newEmailType = EmailType(
            type = command.type,
            description = command.description
        ).also {
            it.setAuditFields(userId, dateTimeHelper.getCurrentDateTime())
        }

        return emailTypeRepository.save(newEmailType)
            .map { getEmailTypeDTOHelper.toDTO(it) }
            .map { EmailTypeViewModel(it) }
    }
}
