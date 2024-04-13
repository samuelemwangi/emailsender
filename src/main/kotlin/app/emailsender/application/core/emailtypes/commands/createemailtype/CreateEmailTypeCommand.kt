package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.BaseCommand
import jakarta.validation.constraints.NotBlank

data class CreateEmailTypeCommand(
    @NotBlank
    val type: String,

    @NotBlank
    val description: String
) : BaseCommand()
