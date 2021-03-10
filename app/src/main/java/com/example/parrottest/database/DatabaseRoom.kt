package com.example.parrottest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parrottest.database.dao.CategoryDAO
import com.example.parrottest.database.dao.ProductDAO
import com.example.parrottest.database.dao.StoreDAO
import com.example.parrottest.database.dao.UserDAO
import com.example.parrottest.database.model.CategoryRoomModel
import com.example.parrottest.database.model.ProductRoomModel
import com.example.parrottest.database.model.StoreRoomModel
import com.example.parrottest.database.model.UserRoomModel

@Database(
    entities = [UserRoomModel::class, CategoryRoomModel::class
        , ProductRoomModel::class, StoreRoomModel::class] ,
    version = 1
)
abstract class DatabaseRoom: RoomDatabase() {

    abstract fun categoriesDAO(): CategoryDAO

    abstract fun productDAO(): ProductDAO

    abstract fun storeDAO(): StoreDAO

    abstract fun usersDAO(): UserDAO

    companion object {

        private const val DATABASE_NAME = "parrot_test_db"

        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getInstance(context: Context): DatabaseRoom? {

            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }
}