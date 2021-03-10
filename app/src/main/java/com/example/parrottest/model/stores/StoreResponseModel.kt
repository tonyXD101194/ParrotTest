package com.example.parrottest.model.stores

import com.google.gson.annotations.SerializedName

class StoreResponseModel(

    @SerializedName("status")
    val status: String,

    @SerializedName("result")
    val result: ResultStoreModel
)