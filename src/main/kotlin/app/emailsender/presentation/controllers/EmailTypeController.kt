package app.emailsender.presentation.controllers

import app.emailsender.application.core.emailtypes.commands.createemailtype.CreateEmailTypeCommand
import app.emailsender.application.core.emailtypes.commands.deleteemailtype.DeleteEmailTypeCommand
import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.queries.getemailtypes.GetEmailTypesQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypesViewModel
import app.emailsender.application.core.extensions.recordOwnedByCurrentUser
import app.emailsender.application.core.extensions.resolveCreateDownloadRights
import app.emailsender.application.core.extensions.resolveEditDeleteRights
import app.emailsender.application.core.extensions.resolveRequestStatus
import app.emailsender.application.core.extensions.resolveStatusMessage
import app.emailsender.application.core.interfaces.CreateItemCommandHandler
import app.emailsender.application.core.interfaces.GetItemQueryHandler
import app.emailsender.application.core.interfaces.GetItemsQueryHandler
import app.emailsender.application.core.interfaces.UpdateItemCommandHandler
import app.emailsender.application.enums.EntityTypes
import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.application.enums.RequestStatus
import app.emailsender.presentation.helpers.AppHeaders
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/email-types")
class EmailTypeController(
    private val getEmailTypeQueryHandler: GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>,
    private val getEmailTypesQueryHandler: GetItemsQueryHandler<GetEmailTypesQuery, EmailTypesViewModel>,
    private val createEmailTypeCommandHandler: CreateItemCommandHandler<CreateEmailTypeCommand, EmailTypeViewModel>,
    private val deleteEmailTypeCommandHandler: UpdateItemCommandHandler<DeleteEmailTypeCommand, EmailTypeViewModel>
) {

    @GetMapping("")
    fun getItems(
        @RequestHeader(AppHeaders.X_USER_ID, required = false) userId: String?,
        @RequestHeader(AppHeaders.X_USER_SCOPES, required = false) userScopes: String?
    ): Mono<ResponseEntity<EmailTypesViewModel>> {

        val query = GetEmailTypesQuery()

        return getEmailTypesQueryHandler.getItems(query)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.FETCH_ITEMS_SUCCESSFUL)

                it.resolveCreateDownloadRights(userScopes, EntityTypes.EMAIL_TYPE.labelText)
                ResponseEntity(it, HttpStatus.OK)
            }
    }


    @GetMapping("{id}")
    fun getItem(
        @PathVariable("id") id: Int,
        @RequestHeader(AppHeaders.X_USER_ID, required = false) userId: String?,
        @RequestHeader(AppHeaders.X_USER_SCOPES, required = false) userScopes: String?
    ): Mono<ResponseEntity<EmailTypeViewModel>> {

        val query = GetEmailTypeQuery(id)

        return getEmailTypeQueryHandler.getItem(query)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.FETCH_ITEM_SUCCESSFUL)

                val loggedInUserIsOwner = it.emailType.recordOwnedByCurrentUser(userId)
                it.resolveEditDeleteRights(userScopes, EntityTypes.EMAIL_TYPE.labelText, loggedInUserIsOwner)
                ResponseEntity(it, HttpStatus.OK)
            }
    }

    @PostMapping("")
    fun createItem(
        @Valid @RequestBody command: CreateEmailTypeCommand,
        @RequestHeader(AppHeaders.X_USER_ID, required = false) userId: String?,
        @RequestHeader(AppHeaders.X_USER_SCOPES, required = false) userScopes: String?
    ): Mono<ResponseEntity<EmailTypeViewModel>> {

        return createEmailTypeCommandHandler.createItem(command, userId)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.CREATE_ITEM_SUCCESSFUL)

                val loggedInUserIsOwner = it.emailType.recordOwnedByCurrentUser(userId)
                it.resolveEditDeleteRights(userScopes, EntityTypes.EMAIL_TYPE.labelText, loggedInUserIsOwner)
                ResponseEntity(it, HttpStatus.OK)
            }
    }

    @PatchMapping("{id}")
    fun updateItem(
        @PathVariable("id") id: Int,
        @Valid @RequestBody command: DeleteEmailTypeCommand,
        @RequestHeader(AppHeaders.X_USER_ID, required = false) userId: String?,
        @RequestHeader(AppHeaders.X_USER_SCOPES, required = false) userScopes: String?
    ): Mono<ResponseEntity<EmailTypeViewModel>> {

        command.id = id

        return deleteEmailTypeCommandHandler.updateItem(command, userId)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.UPDATE_ITEM_SUCCESSFUL)

                val loggedInUserIsOwner = it.emailType.recordOwnedByCurrentUser(userId)
                it.resolveEditDeleteRights(userScopes, EntityTypes.EMAIL_TYPE.labelText, loggedInUserIsOwner)
                ResponseEntity(it, HttpStatus.OK)
            }
    }
}
