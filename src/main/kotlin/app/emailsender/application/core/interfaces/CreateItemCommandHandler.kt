package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface CreateItemCommandHandler<TCommand, TViewModel> {
    fun createItem(command: TCommand, userId: String?): Mono<TViewModel>
}
