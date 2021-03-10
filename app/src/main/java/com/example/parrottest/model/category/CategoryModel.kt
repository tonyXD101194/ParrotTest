package com.example.parrottest.model.category

import com.google.gson.annotations.SerializedName

class CategoryModel(

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("sortPosition")
    val sortPosition: Int
)