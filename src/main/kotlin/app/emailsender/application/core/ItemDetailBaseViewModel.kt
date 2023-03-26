package app.emailsender.application.core

open class ItemDetailBaseViewModel(
    var editEnabled: Boolean = false,
    var deleteEnabled: Boolean = false,
) : BaseViewModel()