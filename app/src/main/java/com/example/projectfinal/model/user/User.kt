package com.example.projectfinal.model.user



data class User(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: UserData,
    val success: Boolean
)