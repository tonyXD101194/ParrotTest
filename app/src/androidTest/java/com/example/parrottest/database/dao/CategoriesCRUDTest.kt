package com.example.parrottest.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parrottest.database.ParrotDatabaseTestRule
import com.example.parrottest.database.model.CategoryRoomModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoriesCRUDTest {

    @get:Rule
    val rule = ParrotDatabaseTestRule()

    @Test
    fun insertCategoryTest() = runBlocking {

        val category: CategoryRoomModel = rule.category

        rule.categoryRepository.insertCategory(category)

        val categorySaved = rule.categoryRepository.getCategoriesByUsername(category.username)

        assertThat(categorySaved.isNotEmpty()).isTrue()
        assertThat(categorySaved[0]).isEqualTo(category)
    }

    @Test
    fun getCategoriesByUserNameTest() = runBlocking {

        val category: CategoryRoomModel = rule.category

        rule.categoryRepository.insertCategory(category)

        rule.categoryRepository.updateCategory(
            category.copy(
                name = "test category updated"
            )
        )

        val categorySaved = rule.categoryRepository.getCategoriesByUsername(category.username)

        assertThat(categorySaved.isNotEmpty()).isTrue()
        assertThat(categorySaved[0].name).isEqualTo("test category updated")
    }

    @Test
    fun deleteCategoryTest() = runBlocking {

        val category: CategoryRoomModel = rule.category

        rule.categoryRepository.insertCategory(category)

        rule.categoryRepository.delete(category)

        val categorySaved = rule.categoryRepository.getCategoriesByUsername(category.username)

        assertThat(categorySaved.isEmpty()).isTrue()
    }

    @Test
    fun deleteAllCategoriesTest() = runBlocking {

        val category: CategoryRoomModel = rule.category

        for (i in 1..3) {

            rule.categoryRepository.insertCategory(
                category.copy(
                    uuid = "test uuid $i"
                )
            )
        }

        rule.categoryRepository.deleteAllCategories()

        val categorySaved = rule.categoryRepository.getCategoriesByUsername(category.username)

        assertThat(categorySaved.isEmpty()).isTrue()
    }
}