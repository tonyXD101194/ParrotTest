package com.example.parrottest.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parrottest.database.ParrotDatabaseTestRule
import com.example.parrottest.database.model.StoreRoomModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoreCRUDTest {

    @get:Rule
    val rule = ParrotDatabaseTestRule()

    @Test
    fun insertStoreTest() = runBlocking {

        val store: StoreRoomModel = rule.store

        rule.storeRepository.insertStore(store)

        val stores = rule.storeRepository.getStoresByUsername(store.username)

        assertThat(stores.isNotEmpty()).isTrue()
        assertThat(stores[0].name).isEqualTo(store.name)
    }

    @Test
    fun updateStoreTest() = runBlocking {

        val store: StoreRoomModel = rule.store

        rule.storeRepository.insertStore(store)

        rule.storeRepository.updateStore(
            store.copy(
                name = "test name updated"
            )
        )

        val stores = rule.storeRepository.getStoresByUsername(store.username)

        assertThat(stores.isNotEmpty()).isTrue()
        assertThat(stores[0].name).isEqualTo("test name updated")
    }

    @Test
    fun deleteStoreTest() = runBlocking {

        val store: StoreRoomModel = rule.store

        rule.storeRepository.insertStore(store)

        rule.storeRepository.delete(store)

        val stores = rule.storeRepository.getStoresByUsername(store.username)

        assertThat(stores.isEmpty()).isTrue()
    }

    @Test
    fun deleteAllStoreTest() = runBlocking {

        val store: StoreRoomModel = rule.store

        for (i in 1..3) {

            rule.storeRepository.insertStore(
                store.copy(
                    uuid = "uuid test $i"
                )
            )
        }

        rule.storeRepository.deleteAllStores()

        val stores = rule.storeRepository.getStoresByUsername(store.username)

        assertThat(stores.isEmpty()).isTrue()
    }
}