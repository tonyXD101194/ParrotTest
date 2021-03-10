package com.example.parrottest.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import category.CategoryBuilder
import com.example.parrottest.database.model.CategoryRoomModel
import com.example.parrottest.database.model.ProductRoomModel
import com.example.parrottest.database.model.StoreRoomModel
import com.example.parrottest.database.model.UserRoomModel
import com.example.parrottest.repository.CategoryRepository
import com.example.parrottest.repository.ProductRepository
import com.example.parrottest.repository.StoreRepository
import com.example.parrottest.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import product.ProductBuilder
import store.StoreBuilder
import user.UserBuilder
import java.util.*

class ParrotDatabaseTestRule : TestWatcher() {

    val context: Context by lazy {

        ApplicationProvider.getApplicationContext() as Context
    }

    // region dao repository

    val categoryRepository: CategoryRepository by lazy {

        CategoryRepository(ApplicationProvider.getApplicationContext())
    }

    val productRepository: ProductRepository by lazy {

        ProductRepository(ApplicationProvider.getApplicationContext())
    }

    val storeRepository: StoreRepository by lazy {

        StoreRepository(ApplicationProvider.getApplicationContext())
    }

    val userRepository: UserRepository by lazy {

        UserRepository(ApplicationProvider.getApplicationContext())
    }

    // endregion


    // region model builders

    val category: CategoryRoomModel by lazy {

        val timestamp: Long = Date().time

        CategoryBuilder
            .aCategory()
            .withUuid("test uuid $timestamp")
            .withName("test name $timestamp")
            .withUsername("test username $timestamp")
            .withSortPosition(1)
            .build()
    }

    val product: ProductRoomModel by lazy {

        val timestamp: Long = Date().time

        ProductBuilder
            .aProduct()
            .withUuid("test uuid $timestamp")
            .withName("test name $timestamp")
            .withIdCategory("test id category $timestamp")
            .withDescription("test description $timestamp")
            .withPrice("test price $timestamp")
            .withAvailability("test availability $timestamp")
            .withImageUrl("test image url $timestamp")
            .withIsSoldAlone(false)
            .build()
    }

    val store: StoreRoomModel by lazy {

        val timestamp: Long = Date().time

        StoreBuilder
            .aStore()
            .withUuid("test uuid $timestamp")
            .withName("test name $timestamp")
            .withUsername("test username $timestamp")
            .withEmail("test email $timestamp")
            .withAvailabilityState("test availability state $timestamp")
            .build()
    }

    val user: UserRoomModel by lazy {

        val timestamp: Long = Date().time

        UserBuilder
            .anUser()
            .withUsername("test username $timestamp")
            .withAccessToken("test access token $timestamp")
            .withRefreshToken("test refresh token $timestamp")
            .build()
    }

    // endregion

    override fun finished(description: Description?) {
        super.finished(description)

        runBlocking {

            categoryRepository.deleteAllCategories()
            productRepository.deleteAllProducts()
            storeRepository.deleteAllStores()
            userRepository.deleteAllUsers()
        }
    }
}