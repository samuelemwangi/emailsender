package app.emailsender.application.core.extensions

import app.emailsender.application.core.BaseViewModel
import app.emailsender.application.core.ItemDetailBaseViewModel
import app.emailsender.application.core.ItemsBaseViewModel
import app.emailsender.application.enums.ItemStatusMessage
import app.emailsender.application.enums.RequestStatus


fun BaseViewModel.resolveRequestStatus(requestStatus: RequestStatus) {
    this.requestStatus = requestStatus.labelText
}

fun BaseViewModel.resolveStatusMessage(statusMessage: ItemStatusMessage, customMessage: String? = null) {
    this.statusMessage = customMessage ?: statusMessage.labelText
}

fun ItemDetailBaseViewModel.resolveEditDeleteRights(userScopes: String?, entity: String, isLoggedInUser: Boolean) {
    this.editEnabled = isLoggedInUser || userScopes?.lowercase()?.contains(entity.lowercase() + ":edit") == true
    this.deleteEnabled = isLoggedInUser || userScopes?.lowercase()?.contains(entity.lowercase() + ":delete") == true
}

fun ItemsBaseViewModel.resolveCreateDownloadRights(userScopes: String?, entity: String) {
    this.createEnabled = userScopes?.lowercase()?.contains(entity.lowercase() + ":create") == true
    this.downloadEnabled = userScopes?.lowercase()?.contains(entity.lowercase() + ":download") == true
}
