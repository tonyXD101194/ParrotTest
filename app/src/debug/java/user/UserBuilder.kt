package user

import androidx.annotation.VisibleForTesting
import com.example.parrottest.database.model.UserRoomModel

@VisibleForTesting
class UserBuilder private constructor() {

    private var username: String = ""
    private var accessToken: String = ""
    private var refreshToken: String = ""

    fun withUsername(value: String): UserBuilder {

        this.username = value
        return this
    }

    fun withAccessToken(value: String): UserBuilder {

        this.accessToken = value
        return this
    }

    fun withRefreshToken(value: String): UserBuilder {

        this.refreshToken = value
        return this
    }

    fun build(): UserRoomModel {

        return UserRoomModel(
            username = this.username,
            accessToken = this.accessToken,
            refreshToken = this.refreshToken
        )
    }

    companion object {

        fun anUser(): UserBuilder {

            return UserBuilder()
        }
    }
}