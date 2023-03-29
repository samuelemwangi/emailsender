package app.emailsender.application.core.emailtypes.commands.updateemailtype

import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.core.interfaces.UpdateItemCommandHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.updateAuditFields
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdateEmailTypeCommandHandler(
    private val emailTypeRepository: EmailTypeRepository,
    private val dateTimeHelper: DateTimeHelper,
    private val getItemQueryHandler: GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>
) : UpdateItemCommandHandler<UpdateEmailTypeCommand, EmailTypeViewModel> {

    override fun updateItem(command: UpdateEmailTypeCommand): Mono<EmailTypeViewModel> {
        if (command.id == null) {
            throw IllegalArgumentException("Id cannot be null")
        }
        var emailType = emailTypeRepository.findById(command.id!!).toFuture().get()
            ?: throw NoRecordException("${command.id}", EntityTypes.EMAIL_TYPE.labelText)

        emailType.type = command.type ?: emailType.type
        emailType.description = command.description ?: emailType.description

        emailType.updateAuditFields(command.userId, dateTimeHelper.getCurrentDateTime())

        return emailTypeRepository.save(emailType)
            .map { getItemQueryHandler.getItem(GetEmailTypeQuery(it.id)) }
            .flatMap { it }
    }
}