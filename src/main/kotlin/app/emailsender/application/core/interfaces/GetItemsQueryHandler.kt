package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface GetItemsQueryHandler<TQuery, TViewModel> {
    fun getItems(query: TQuery): Mono<TViewModel>
}