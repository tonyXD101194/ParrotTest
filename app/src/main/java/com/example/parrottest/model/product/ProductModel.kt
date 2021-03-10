package com.example.parrottest.model.product

import com.example.parrottest.model.category.CategoryModel
import com.google.gson.annotations.SerializedName

class ProductModel (

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("legacyId")
    val legacyId: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("alcoholCount")
    val alcoholCount: Int,

    @SerializedName("soldAlone")
    val soldAlone: Boolean,

    @SerializedName("availability")
    val availability: String,

    @SerializedName("providerAvailability")
    var providerAvailability: String? = null,

    @SerializedName("category")
    val category: CategoryModel
)