package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface CreateItemCommandHandler<TCommand, TResult> {
    fun createItem(command: TCommand): Mono<TResult>
}