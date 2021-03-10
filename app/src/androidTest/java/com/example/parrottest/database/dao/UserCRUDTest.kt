package com.example.parrottest.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parrottest.database.ParrotDatabaseTestRule
import com.example.parrottest.database.model.UserRoomModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserCRUDTest {

    @get:Rule
    val rule = ParrotDatabaseTestRule()

    @Test
    fun insertUserTest() = runBlocking {

        val user: UserRoomModel = rule.user

        rule.userRepository.insertUser(user)

        val userSaved = rule.userRepository.getUserByUserName(user.username)

        assertThat(userSaved != null).isTrue()
        assertThat(userSaved!!.username).isEqualTo(user.username)
    }

    @Test
    fun updateUserTest() = runBlocking {

        val user: UserRoomModel = rule.user

        rule.userRepository.insertUser(user)

        rule.userRepository.updateUser(
            user.copy(
                accessToken = "test access token updated"
            )
        )

        val userSaved = rule.userRepository.getUserByUserName(user.username)

        assertThat(userSaved != null).isTrue()
        assertThat(userSaved!!.accessToken).isEqualTo("test access token updated")
    }

    @Test
    fun deleteUserTest() = runBlocking {

        val user: UserRoomModel = rule.user

        rule.userRepository.insertUser(user)

        rule.userRepository.delete(user)

        val userSaved = rule.userRepository.getUserByUserName(user.username)

        assertThat(userSaved == null).isTrue()
    }

    @Test
    fun deleteAllUsersTest() = runBlocking {

        val user: UserRoomModel = rule.user

        for (i in 1..3) {

            rule.userRepository.insertUser(
                user.copy(
                    accessToken = "test access token $i"
                )
            )
        }

        rule.userRepository.deleteAllUsers()

        val userSaved = rule.userRepository.getUserByUserName(user.username)

        assertThat(userSaved == null).isTrue()
    }
}