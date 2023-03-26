package app.emailsender.application.core

import reactor.core.publisher.Mono

interface GetItemsQueryHandler<TQuery, TResult> {
    fun getItems(query: TQuery): Mono<TResult>
}