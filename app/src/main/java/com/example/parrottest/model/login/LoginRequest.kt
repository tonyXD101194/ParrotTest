package com.example.parrottest.model.login

import com.google.gson.annotations.SerializedName

class LoginRequest (

    @SerializedName("username")
    val userName: String,

    @SerializedName("password")
    val password: String
)