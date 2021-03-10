package com.example.parrottest.repository

import android.app.Application
import com.example.parrottest.database.DatabaseRoom
import com.example.parrottest.database.dao.StoreDAO
import com.example.parrottest.database.model.StoreRoomModel

class StoreRepository(
    application: Application
) {

    private val storeDAO: StoreDAO? = DatabaseRoom.getInstance(application)?.storeDAO()

    suspend fun insertStore(store: StoreRoomModel) {

        return storeDAO!!.insert(store)
    }

    suspend fun getStoresByUsername(username: String): List<StoreRoomModel> {

        return storeDAO!!.getStoresByUsername(username)
    }

    suspend fun updateStore(store: StoreRoomModel) {

        return storeDAO!!.update(store)
    }

    suspend fun delete(store: StoreRoomModel) {

        return storeDAO!!.delete(store)
    }

    suspend fun deleteAllStores() {

        return storeDAO!!.deleteAll()
    }
}