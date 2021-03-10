package com.example.parrottest.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductRoomModel (

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,

    @ColumnInfo(name = "id_category")
    val idCategory: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "price")
    val price: String,

    @ColumnInfo(name = "sold_alone")
    val isSoldAlone: Boolean,

    @ColumnInfo(name = "availability")
    var availability: String
)