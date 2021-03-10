package com.example.parrottest.repository

import android.app.Application
import com.example.parrottest.database.DatabaseRoom
import com.example.parrottest.database.dao.CategoryDAO
import com.example.parrottest.database.model.CategoryRoomModel

class CategoryRepository(
    application: Application
) {

    private val categoryDao: CategoryDAO? = DatabaseRoom.getInstance(application)?.categoriesDAO()

    suspend fun insertCategory(category: CategoryRoomModel) {

        return categoryDao!!.insert(category)
    }

    suspend fun getCategoriesByUsername(username: String): List<CategoryRoomModel> {

        return categoryDao!!.getCategoriesByUsername(username)
    }

    suspend fun updateCategory(category: CategoryRoomModel) {

        return categoryDao!!.update(category)
    }

    suspend fun delete(category: CategoryRoomModel) {

        return categoryDao!!.delete(category)
    }

    suspend fun deleteAll() {

        categoryDao!!.deleteAll()
    }
}