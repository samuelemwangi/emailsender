package app.emailsender.application.core

abstract class ItemDetailBaseViewModel(
    var editEnabled: Boolean = false,
    var deleteEnabled: Boolean = false
) : BaseViewModel()
