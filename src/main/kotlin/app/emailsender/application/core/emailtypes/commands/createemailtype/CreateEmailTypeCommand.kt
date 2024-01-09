package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.BaseCommand
import javax.validation.constraints.NotBlank

data class CreateEmailTypeCommand(
    val type: String,
    @NotBlank
    val description: String
) : BaseCommand()
