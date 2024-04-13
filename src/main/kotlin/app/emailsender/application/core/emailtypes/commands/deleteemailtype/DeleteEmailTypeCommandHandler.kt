package app.emailsender.application.core.emailtypes.commands.deleteemailtype

import app.emailsender.application.core.DeleteRecordViewModel
import app.emailsender.application.core.interfaces.DeleteItemCommandHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.interfaces.DateTimeHelper
import app.emailsender.domain.updateAuditFields
import app.emailsender.persistence.mysql.repositories.EmailTypeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteEmailTypeCommandHandler(
    private val emailTypeRepository: EmailTypeRepository,
    private val dateTimeHelper: DateTimeHelper
) : DeleteItemCommandHandler<DeleteEmailTypeCommand, DeleteRecordViewModel> {

    override fun deleteItem(command: DeleteEmailTypeCommand, userId: String?): Mono<DeleteRecordViewModel> {

        val emailType = emailTypeRepository.findById(command.id).toFuture().get()
            ?: throw NoRecordException("${command.id}", EntityTypes.EMAIL_TYPE.labelText)

        emailType.updateAuditFields(userId, dateTimeHelper.getCurrentDateTime(), true)

        return emailTypeRepository.save(emailType)
            .map { DeleteRecordViewModel() }
    }
}
