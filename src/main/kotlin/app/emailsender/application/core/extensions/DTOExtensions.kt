package app.emailsender.application.core.extensions

import app.emailsender.application.core.BaseEntityDTO

fun BaseEntityDTO.ownedByLoggedInUser(userid: String?): Boolean {
    return userid != null && (this.createdBy == userid || this.lastModifiedBy == userid)
}