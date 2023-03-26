package app.emailsender.application.core

open class ItemsBaseViewModel(
    var createEnabled: Boolean = false,
    var downloadEnabled: Boolean = false,
) : BaseViewModel()