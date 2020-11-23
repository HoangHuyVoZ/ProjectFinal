package com.example.projectfinal.model.login


data class LoginData(
    val accessToken: String,
    val avatar: String,
    val refreshToken: String,
    val role: String,
    val userId: String
)