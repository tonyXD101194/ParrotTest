package com.example.parrottest.repository

import android.app.Application
import com.example.parrottest.database.DatabaseRoom
import com.example.parrottest.database.dao.UserDAO
import com.example.parrottest.database.model.UserRoomModel

class UserRepository(
    application: Application
) {

    private val userDAO: UserDAO? = DatabaseRoom.getInstance(application)?.usersDAO()

    suspend fun insertUser(user: UserRoomModel) {

        return userDAO!!.insert(user)
    }

    suspend fun getUserByUserName(username: String): UserRoomModel? {

        return userDAO!!.getUserByUserName(username)
    }

    suspend fun updateUser(user: UserRoomModel) {

        return userDAO!!.update(user)
    }

    suspend fun delete(user: UserRoomModel) {

        return userDAO!!.delete(user)
    }

    suspend fun deleteAllUsers() {

        return userDAO!!.deleteAll()
    }
}