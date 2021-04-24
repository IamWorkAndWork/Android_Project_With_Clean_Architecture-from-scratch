package app.project.ui.mapper

import app.project.domain.entity.UserAccount
import app.project.ui.model.UserAccountUIModel

class UserAccountUIModelMapper {
    fun transformAccount(userAccount: UserAccount): UserAccountUIModel {
        return UserAccountUIModel(
            id = userAccount.id,
            username = userAccount.username,
            email = userAccount.email,
            firstName = userAccount.firstname,
            lastName = userAccount.lastname
        )
    }
}