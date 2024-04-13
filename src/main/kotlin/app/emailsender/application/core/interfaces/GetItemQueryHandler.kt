package app.emailsender.application.core.interfaces

import reactor.core.publisher.Mono

interface GetItemQueryHandler<TQuery, TViewModel> {
    fun getItem(query: TQuery): Mono<TViewModel>
}
