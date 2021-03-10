package com.example.parrottest.model.product

import com.google.gson.annotations.SerializedName

class ProductStatusModel(

    @SerializedName("status")
    val status: String,

    @SerializedName("result")
    val product: ProductModel
)