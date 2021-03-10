package com.example.parrottest.model.stores

import com.google.gson.annotations.SerializedName

class StoreModel(

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("availabilityState")
    val availabilityState: String

//    TODO missing a complete response to know the type of this variables
//    @SerializedName("providers")
//    val providers: Array<Any>,
//
//    @SerializedName("config")
//    var config: String? = null,
//
//    @SerializedName("fiscalInformation")
//    val fiscalInformation: String
)