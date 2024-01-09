package app.emailsender.application.core.emailtypes.viewmodels

import app.emailsender.application.core.ItemDetailBaseViewModel

data class EmailTypeViewModel(
    val emailType: EmailTypeDTO
) : ItemDetailBaseViewModel()
