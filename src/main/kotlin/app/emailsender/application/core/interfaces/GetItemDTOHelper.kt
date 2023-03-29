package app.emailsender.application.core.interfaces

interface GetItemDTOHelper<TEntity, TDTO>{
    fun toDTO(entity: TEntity): TDTO
}