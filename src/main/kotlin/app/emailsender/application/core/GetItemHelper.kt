package app.emailsender.application.core

interface GetItemHelper<TEntity, TDto>{
    fun toDto(entity: TEntity): TDto
}