package app.emailsender.application.core

import reactor.core.publisher.Mono

interface CreateItemCommandHandler<TCommand, TResult> {
    fun createItem(command: TCommand): Mono<TResult>
}