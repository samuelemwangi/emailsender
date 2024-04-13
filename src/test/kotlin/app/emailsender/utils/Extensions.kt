package app.emailsender.utils

import app.emailsender.application.core.BaseEntityDTO
import app.emailsender.application.core.BaseViewModel
import app.emailsender.application.core.ItemDetailBaseViewModel
import app.emailsender.application.core.ItemsBaseViewModel
import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.domain.BaseEntity
import app.emailsender.domain.emailtypes.EmailType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import java.time.LocalDateTime
import kotlin.reflect.full.memberProperties

internal fun LocalDateTime.toDateTimeString(): String {
    return this.toString()
}

internal fun getObjectPropValues(value: Any) =
    value::class.memberProperties
        .sortedBy { it.name }.associateTo(HashMap()) { p -> p.name to p.call(value).toString() }

internal fun validateEquals(
    expected: Any?,
    actual: Any?,
    propertyName: String = "property"
) {
    assertEquals(expected, actual, "expected '$propertyName' to equal '$expected' but was '$actual'")
}

internal fun validateNotNull(
    actual: Any?,
    propertyName: String = "property"
) {
    assertNotNull(actual, "expected '$propertyName' to not be null but it was")
}

internal fun validateObjectPropValues(
    expected: Any,
    actual: Any,
    propertiesToSkip: HashSet<String> = hashSetOf("isDeleted")
): Boolean {

    val expectedProps = getObjectPropValues(expected)
    val actualProps = getObjectPropValues(actual)

    expectedProps.forEach { (key, value) ->
        if (propertiesToSkip.contains(key)) return@forEach
        validateEquals(value, actualProps[key], key)
    }

    return true
}

internal fun BaseViewModel.validateBaseVMFields(
    expectedRequestStatus: String = "",
    expectedStatusMessage: String = "",
): Boolean {

    validateEquals(
        expectedRequestStatus,
        this.requestStatus,
        "requestStatus"
    )

    validateEquals(
        expectedStatusMessage,
        this.statusMessage,
        "statusMessage"
    )

    return true
}

internal fun ItemsBaseViewModel.validateItemsBaseViewModelFields(
    createdEnabled: Boolean = false,
    downloadEnabled: Boolean = false,
): Boolean {

    validateEquals(
        createdEnabled,
        this.createEnabled,
        "createEnabled"
    )

    validateEquals(
        downloadEnabled,
        this.downloadEnabled,
        "downloadEnabled"
    )

    return true
}

internal fun ItemDetailBaseViewModel.validateItemDetailBaseVMFields(
    editEnabled: Boolean = false,
    deleteEnabled: Boolean = false,
): Boolean {

    validateEquals(
        editEnabled,
        this.editEnabled,
        "editEnabled"
    )

    validateEquals(
        deleteEnabled,
        this.deleteEnabled,
        "deleteEnabled"
    )

    return true
}

fun BaseEntityDTO.validateDTOAuditFields(
    item: BaseEntity,
): Boolean {

    validateEquals(
        item.createdAt?.toDateTimeString(),
        this.createdAt,
        "createdAt"
    )

    validateEquals(
        item.createdBy,
        this.createdBy,
        "createdBy"
    )

    validateEquals(
        item.updatedAt?.toDateTimeString(),
        this.updatedAt,
        "updatedAt"
    )

    validateEquals(
        item.updatedBy,
        this.updatedBy,
        "updatedBy"
    )

    return true
}

fun EmailTypeDTO.validateEmailTypeDTOFields(item: EmailType): Boolean {
    validateEquals(item.id, this.id, "id")
    validateEquals(item.type, this.type, "type")
    validateEquals(item.description, this.description, "description")

    this.validateDTOAuditFields(item)

    return true
}
