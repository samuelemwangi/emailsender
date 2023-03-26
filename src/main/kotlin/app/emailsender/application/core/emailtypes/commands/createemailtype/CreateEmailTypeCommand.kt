package app.emailsender.application.core.emailtypes.commands.createemailtype

import app.emailsender.application.core.BaseCommand
import javax.validation.constraints.NotNull

data class CreateEmailTypeCommand(
    @field:NotNull val type: String,
    @field:NotNull val description: String
) : BaseCommand()