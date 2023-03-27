package app.emailsender.application.core.interfaces

interface GetItemHelper<TEntity, TDto>{
    fun toDto(entity: TEntity): TDto
}