package app.emailsender.unittest.presentation.controllers

import app.emailsender.application.core.BaseViewModel
import app.emailsender.application.core.ItemDetailBaseViewModel
import app.emailsender.application.core.ItemsBaseViewModel
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.randomUUID
import app.emailsender.utils.validateEquals
import app.emailsender.utils.validateNotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal open class ControllersTest : BaseTest() {

    protected val userId = randomUUID()
    protected val userScopes = "admin:create"

    private fun <T> validateResponseEntity(responseEntity: ResponseEntity<T>, httpStatus: HttpStatus): T? {
        validateNotNull(responseEntity, "responseEntity")
        validateEquals(responseEntity.statusCode, httpStatus, "httpStatus")
        return responseEntity.body
    }

    protected fun <T : BaseViewModel> validateBaseViewModel(
        responseEntity: ResponseEntity<T>,
        httpStatus: HttpStatus,
        expectedResponseMessage: String = "Item deleted successfully",
        expectedResponseStatus: String = "Request successful"
    ): T? {
        val viewModel = validateResponseEntity(responseEntity, httpStatus)
        validateNotNull(viewModel, "viewModel")
        validateEquals(expectedResponseMessage, viewModel?.statusMessage, "statusMessage")
        validateEquals(expectedResponseStatus, viewModel?.requestStatus, "requestStatus")
        return viewModel
    }

    protected fun <T : ItemsBaseViewModel> validateItemsBaseViewModel(
        responseEntity: ResponseEntity<T>,
        httpStatus: HttpStatus = HttpStatus.OK,
        expectedResponseMessage: String = "Items fetched successfully",
        expectedResponseStatus: String = "Request successful",
        createEnabled: Boolean = false,
        downloadEnabled: Boolean = false
    ): T? {
        val viewModel =
            validateBaseViewModel(responseEntity, httpStatus, expectedResponseMessage, expectedResponseStatus)
        validateEquals(createEnabled, viewModel?.createEnabled, "createEnabled")
        validateEquals(downloadEnabled, viewModel?.downloadEnabled, "downloadEnabled")
        return viewModel
    }

    protected fun <T : ItemDetailBaseViewModel> validateItemDetailBaseViewModel(
        responseEntity: ResponseEntity<T>,
        httpStatus: HttpStatus = HttpStatus.OK,
        expectedResponseMessage: String = "Item fetched successfully",
        expectedResponseStatus: String = "Request successful",
        editEnabled: Boolean = false,
        deleteEnabled: Boolean = false
    ): T? {
        val viewModel =
            validateBaseViewModel(responseEntity, httpStatus, expectedResponseMessage, expectedResponseStatus)
        validateEquals(editEnabled, viewModel?.editEnabled, "editEnabled")
        validateEquals(deleteEnabled, viewModel?.deleteEnabled, "deleteEnabled")
        return viewModel
    }
}
