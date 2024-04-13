package app.emailsender.application.core.emailtypes.viewmodels

import app.emailsender.application.core.ItemsBaseViewModel

data class EmailTypesViewModel(
    val emailTypes: List<EmailTypeDTO>
) : ItemsBaseViewModel()
