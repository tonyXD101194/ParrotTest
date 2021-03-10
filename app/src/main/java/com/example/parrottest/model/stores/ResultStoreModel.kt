package com.example.parrottest.model.stores

import com.google.gson.annotations.SerializedName

class ResultStoreModel(

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("stores")
    val stores: List<StoreModel>
) {
}