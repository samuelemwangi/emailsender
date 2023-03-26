package app.emailsender.presentation.controllers

import app.emailsender.application.core.CreateItemCommandHandler
import app.emailsender.application.core.GetItemQueryHandler
import app.emailsender.application.core.GetItemsQueryHandler
import app.emailsender.application.core.emailtypes.commands.createemailtype.CreateEmailTypeCommand
import app.emailsender.application.core.emailtypes.queries.getemailtype.GetEmailTypeQuery
import app.emailsender.application.core.emailtypes.queries.getemailtypes.GetEmailTypesQuery
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeViewModel
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypesViewModel
import app.emailsender.application.core.extensions.ownedByLoggedInUser
import app.emailsender.application.core.extensions.resolveCreateDownloadRights
import app.emailsender.application.core.extensions.resolveEditDeleteRights
import app.emailsender.application.core.extensions.resolveRequestStatus
import app.emailsender.application.core.extensions.resolveStatusMessage
import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.application.enums.RequestStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/email-types")
class EmailTypeController(
    private val getEmailTypeQueryHandler: GetItemQueryHandler<GetEmailTypeQuery, EmailTypeViewModel>,
    private val getEmailTypesQueryHandler: GetItemsQueryHandler<GetEmailTypesQuery, EmailTypesViewModel>,
    private val createEmailTypeCommandHandler: CreateItemCommandHandler<CreateEmailTypeCommand, EmailTypeViewModel>
) {

    @GetMapping("")
    fun getItems(): Mono<ResponseEntity<EmailTypesViewModel>> {
        val query = GetEmailTypesQuery()

        return getEmailTypesQueryHandler.getItems(query)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.SUCCESS)

                it.resolveCreateDownloadRights(null, "EmailType")
                ResponseEntity(it, HttpStatus.OK)
            }
    }


    @GetMapping("{id}")
    fun getItem(@PathVariable("id") id: Int): Mono<ResponseEntity<EmailTypeViewModel>> {
        val query = GetEmailTypeQuery(id)

        return getEmailTypeQueryHandler.getItem(query)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.SUCCESS)

                val isLoggedUserOwner = it.emailType.ownedByLoggedInUser(null)
                it.resolveEditDeleteRights(null, "EmailType", isLoggedUserOwner)
                ResponseEntity(it, HttpStatus.OK)
            }
    }

    @PostMapping("")
    fun createItem(@Valid @RequestBody command: CreateEmailTypeCommand): Mono<ResponseEntity<EmailTypeViewModel>> {

        return createEmailTypeCommandHandler.createItem(command)
            .map {
                it.resolveRequestStatus(RequestStatus.SUCCESSFUL)
                it.resolveStatusMessage(ItemStatusMessage.SUCCESS)

                val isLoggedUserOwner = it.emailType.ownedByLoggedInUser(null)
                it.resolveEditDeleteRights(null, "EmailType", isLoggedUserOwner)
                ResponseEntity(it, HttpStatus.OK)
            }
    }

}


