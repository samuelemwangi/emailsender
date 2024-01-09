package app.emailsender.application.core

abstract class ItemsBaseViewModel(
    var createEnabled: Boolean = false,
    var downloadEnabled: Boolean = false
) : BaseViewModel()
