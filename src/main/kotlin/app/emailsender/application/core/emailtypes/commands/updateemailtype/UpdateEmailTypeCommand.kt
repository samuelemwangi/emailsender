package app.emailsender.application.core.emailtypes.commands.updateemailtype

import app.emailsender.application.core.BaseCommand

data class UpdateEmailTypeCommand(
    val id: Int?,
    val type: String?,
    val description: String?
) : BaseCommand()
