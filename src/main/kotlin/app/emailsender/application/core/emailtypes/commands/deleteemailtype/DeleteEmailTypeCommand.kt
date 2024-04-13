package app.emailsender.application.core.emailtypes.commands.deleteemailtype

import app.emailsender.application.core.BaseCommand

data class DeleteEmailTypeCommand(
    var id: Int,
) : BaseCommand()
