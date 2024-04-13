package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface DeleteItemCommandHandler<TCommand, TViewModel> {
    fun deleteItem(command: TCommand, userId: String?): Mono<TViewModel>
}
