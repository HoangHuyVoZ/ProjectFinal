package com.example.projectfinal.model.login


import com.google.gson.annotations.SerializedName

data class Login(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: LoginData,
    val success: Boolean
)