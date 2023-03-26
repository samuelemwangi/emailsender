package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.BaseCommand
import javax.validation.constraints.NotNull

class CreateEmailTypeCommand(
    @NotNull(message = "Type cannot be null")
    val type: String,

    @NotNull(message = "Description cannot be null")
    val description: String
) : BaseCommand()