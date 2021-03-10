package com.example.parrottest.repository

import android.app.Application
import com.example.parrottest.database.DatabaseRoom
import com.example.parrottest.database.dao.ProductDAO
import com.example.parrottest.database.model.ProductRoomModel

class ProductRepository(
    application: Application
) {

    private val productDAO: ProductDAO? = DatabaseRoom.getInstance(application)?.productDAO()

    suspend fun insertProduct(product: ProductRoomModel) {

        return productDAO!!.insert(product)
    }

    suspend fun getProductById(productId: String): ProductRoomModel? {

        return productDAO!!.getProductById(productId)
    }

    suspend fun getProductsByCategory(idCategory: String): List<ProductRoomModel> {

        return productDAO!!.getProductsByCategory(idCategory)
    }

    suspend fun updateProduct(product: ProductRoomModel) {

        return productDAO!!.update(product)
    }

    suspend fun deleteProduct(product: ProductRoomModel) {

        return productDAO!!.delete(product)
    }

    suspend fun deleteAllProducts() {

        return productDAO!!.deleteAll()
    }
}