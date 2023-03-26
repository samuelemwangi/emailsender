package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.CreateItemCommandHandler
import app.emailsender.application.core.GetItemQueryHandler
import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
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
    private val getItemQueryHandler: GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>,
    private val dateTimeHelper: DateTimeHelper
) : CreateItemCommandHandler<CreateEmailTypeCommand, EmailTypeViewModel> {

    override fun createItem(command: CreateEmailTypeCommand): Mono<EmailTypeViewModel> {
        val emailType = emailTypeRepository.findByType(command.type!!).toFuture().get();
        if (emailType != null) {
            throw RecordExistsException(command.type, "email type")
        }

        val newEmailType = EmailType(
            type = command.type,
            description = command.description
        )

        newEmailType.setAuditFields(command.userId, dateTimeHelper.getCurrentDateTime())

        return emailTypeRepository.save(newEmailType)
            .map { getItemQueryHandler.getItem(GetEmailTypeQuery(it.id)) }
            .flatMap { it }
    }
}