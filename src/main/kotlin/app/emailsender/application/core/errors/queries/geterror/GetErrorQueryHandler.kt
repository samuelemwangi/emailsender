package app.emailsender.application.core.errors.queries.geterror

import app.emailsender.application.core.errors.viewmodels.ErrorDTO
import app.emailsender.application.core.errors.viewmodels.ErrorViewModel
import app.emailsender.application.core.extensions.resolveRequestStatus
import app.emailsender.application.core.extensions.resolveStatusMessage
import app.emailsender.application.core.interfaces.GetItemDTOHelper
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.application.enums.RequestStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class GetErrorQueryHandler(
) : GetItemQueryHandler<GetErrorQuery, ErrorViewModel>, GetItemDTOHelper<GetErrorQuery, ErrorDTO> {

    override fun getItem(query: GetErrorQuery): Mono<ErrorViewModel> {
        return Mono.just(toViewModel(query))
    }

    override fun toDTO(entity: GetErrorQuery): ErrorDTO {
        return ErrorDTO(
            message = entity.errorMessage,
            timestamp = Instant.now()
        )
    }

    private fun toViewModel(query: GetErrorQuery): ErrorViewModel {
        val errorDetail = toDTO(query)
        val errorVM = ErrorViewModel(error = errorDetail)
        errorVM.resolveRequestStatus(RequestStatus.FAILED)
        errorVM.resolveStatusMessage(
            ItemStatusMessage.FAILED,
            query.httpStatus.toString().replace(" ", " - ").replace("_", " ")
        )
        return errorVM
    }
}