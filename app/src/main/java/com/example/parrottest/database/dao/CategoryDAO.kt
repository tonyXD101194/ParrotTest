package com.example.parrottest.database.dao

import androidx.room.*
import com.example.parrottest.database.model.CategoryRoomModel

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryRoomModel)

    @Query("SELECT * FROM categories WHERE username = :username ORDER BY sort_position ASC")
    suspend fun getCategoriesByUsername(username: String): List<CategoryRoomModel>

    @Update
    suspend fun update(category: CategoryRoomModel)

    @Delete
    suspend fun delete(category: CategoryRoomModel)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}