package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface UpdateItemCommandHandler<TCommand, TViewModel> {
    fun updateItem(command: TCommand): Mono<TViewModel>
}