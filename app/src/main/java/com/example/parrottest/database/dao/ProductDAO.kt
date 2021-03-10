package com.example.parrottest.database.dao

import androidx.room.*
import com.example.parrottest.database.model.ProductRoomModel

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductRoomModel)

    @Query("SELECT * FROM products WHERE uuid = :productId")
    suspend fun getProductById(productId: String): ProductRoomModel?

    @Query("SELECT * FROM products WHERE id_category = :idCategory")
    suspend fun getProductsByCategory(idCategory: String): List<ProductRoomModel>

    @Update
    suspend fun update(product: ProductRoomModel)

    @Delete
    suspend fun delete(product: ProductRoomModel)

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}