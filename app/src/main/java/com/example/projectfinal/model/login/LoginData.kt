package com.example.projectfinal.model.login


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("userId")
    val userId: String
)