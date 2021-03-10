package com.example.parrottest.model.product

import com.google.gson.annotations.SerializedName

class ProductResponseModel(

    @SerializedName("status")
    val status: String,

    @SerializedName("results")
    val products: List<ProductModel>
)