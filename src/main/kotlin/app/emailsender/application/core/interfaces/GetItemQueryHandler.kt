package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface GetItemQueryHandler<TQuery, TResult> {
    fun getItem(query: TQuery): Mono<TResult>
}