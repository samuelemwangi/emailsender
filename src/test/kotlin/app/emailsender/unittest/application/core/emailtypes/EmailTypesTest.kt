package app.emailsender.unittest.application.core.emailtypes

import app.emailsender.application.core.emailtypes.viewmodels.EmailTypeDTO
import app.emailsender.domain.emailtypes.EmailType
import app.emailsender.unittest.BaseTest
import app.emailsender.utils.randomInt
import app.emailsender.utils.randomSentence

internal open class EmailTypesTest : BaseTest() {

    fun getEmailType() = EmailType(
        id = randomInt(),
        type = randomSentence(1, true),
        description = randomSentence(3, true)
    ).also {
        it.setAuditFieldsForTest()
    }

    fun transFormToDTO(emailType: EmailType) = EmailTypeDTO(
        id = emailType.id,
        type = emailType.type,
        description = emailType.description
    ).also {
        it.updateDTOAuditFieldsForTest(emailType)
    }
}
