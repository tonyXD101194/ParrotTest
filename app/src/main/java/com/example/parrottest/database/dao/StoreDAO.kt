package com.example.parrottest.database.dao

import androidx.room.*
import com.example.parrottest.database.model.StoreRoomModel

@Dao
interface StoreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(store: StoreRoomModel)

    @Query("SELECT * FROM stores WHERE username = :username")
    suspend fun getStoresByUsername(username: String): List<StoreRoomModel>

    @Update
    suspend fun update(store: StoreRoomModel)

    @Delete
    suspend fun delete(store: StoreRoomModel)

    @Query("DELETE FROM stores")
    suspend fun deleteAll()
}