package app.project.data.mapper

import app.project.domain.entity.UserAccount
import com.parse.ParseUser

class ParseUserMapper {
    fun transform(parseUser: ParseUser): UserAccount {
        return UserAccount(
            id = parseUser.objectId,
            username = parseUser.username,
            email = parseUser.email,
            firstname = parseUser.getString("firstName").orEmpty(),
            lastname = parseUser.getString("lastName").orEmpty()
        )
    }
}