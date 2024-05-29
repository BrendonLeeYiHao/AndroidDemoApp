package com.example.profileapplication.app.main.login.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("name") val username: String,
    @SerializedName("password") val password: String
)