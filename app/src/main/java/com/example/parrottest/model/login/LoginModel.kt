package com.example.parrottest.model.login

import com.google.gson.annotations.SerializedName

class LoginModel (

    @SerializedName("refresh")
    val refresh: String,

    @SerializedName("access")
    val access: String
)