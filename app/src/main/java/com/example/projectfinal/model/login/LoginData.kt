package com.example.projectfinal.model.login


import com.google.gson.annotations.SerializedName

data class LoginData(
    val accessToken: String,
    val avatar: String,
    val refreshToken: String,
    val role: String,
    val userId: String
)