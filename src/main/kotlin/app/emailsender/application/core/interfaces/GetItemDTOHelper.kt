package app.emailsender.application.core.interfaces

interface GetItemDTOHelper<TEntity, TEntityDTO>{
    fun toDTO(entity: TEntity): TEntityDTO
}