package com.example.parrottest.database.dao

import androidx.room.*
import com.example.parrottest.database.model.UserRoomModel

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserRoomModel)

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUserName(username: String): UserRoomModel?

    @Update
    suspend fun update(user: UserRoomModel)

    @Delete
    suspend fun delete(user: UserRoomModel)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}