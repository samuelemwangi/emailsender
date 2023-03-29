package app.emailsender.application.core.emailtypes.commands.updateemailtype

import app.emailsender.application.core.BaseCommand

data class UpdateEmailTypeCommand (
    var id: Int? = null,
    var type: String? = null,
    var description: String? = null
) : BaseCommand()