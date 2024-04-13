package app.emailsender.application.core.errors.queries.geterror

import app.emailsender.application.core.errors.viewmodels.ErrorDTO
import app.emailsender.application.core.errors.viewmodels.ErrorViewModel
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.enums.RequestStatus
import app.emailsender.application.interfaces.DateTimeHelper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class GetErrorQueryHandler(
    private val dateTimeHelper: DateTimeHelper
) : GetItemQueryHandler<GetErrorQuery, ErrorViewModel>,
    GetItemDTOHelper<GetErrorQuery, ErrorDTO> {

    override fun getItem(query: GetErrorQuery): Mono<ErrorViewModel> {
        return Mono.just(toViewModel(query))
    }

    override fun toDTO(entity: GetErrorQuery): ErrorDTO {
        return ErrorDTO(
            message = entity.errorMessage,
            timestamp = dateTimeHelper.getTimeStamp()
        )
    }

    private fun toViewModel(query: GetErrorQuery): ErrorViewModel {

        return ErrorViewModel(
            error = toDTO(query)
        ).also {
            it.requestStatus = RequestStatus.FAILED.labelText
            it.statusMessage = query.httpStatus.toString().replace(" ", " - ").replace("_", " ")
        }
    }
}
