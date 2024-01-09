package app.emailsender.application.core.emailtypes.commands.updateemailtype

import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.core.interfaces.UpdateItemCommandHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.exceptions.BadRequestException
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

    override fun updateItem(command: UpdateEmailTypeCommand, userId: String?): Mono<EmailTypeViewModel> {

        if (command.id == null) {
            throw BadRequestException("Kindly provide a valid ${EntityTypes.EMAIL_TYPE.labelText} Id")
        }

        val emailType = emailTypeRepository.findById(command.id).toFuture().get()
            ?: throw NoRecordException("${command.id}", EntityTypes.EMAIL_TYPE.labelText)

        emailType.type = command.type ?: emailType.type
        emailType.description = command.description ?: emailType.description

        emailType.updateAuditFields(userId, dateTimeHelper.getCurrentDateTime())

        return emailTypeRepository.save(emailType)
            .map { getItemQueryHandler.getItem(GetEmailTypeQuery(it.id)) }
            .flatMap { it }
    }
}
