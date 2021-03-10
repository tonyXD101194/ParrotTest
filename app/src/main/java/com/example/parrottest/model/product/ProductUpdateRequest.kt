package com.example.parrottest.model.product

import com.google.gson.annotations.SerializedName

class ProductUpdateRequest (

    @SerializedName("availability")
    val availability: String
)