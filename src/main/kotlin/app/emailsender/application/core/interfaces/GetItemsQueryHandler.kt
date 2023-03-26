package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface GetItemsQueryHandler<TQuery, TResult> {
    fun getItems(query: TQuery): Mono<TResult>
}