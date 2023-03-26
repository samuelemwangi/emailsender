package app.emailsender.application.core

import reactor.core.publisher.Mono

interface GetItemQueryHandler<TQuery, TResult> {
    fun getItem(query: TQuery): Mono<TResult>
}