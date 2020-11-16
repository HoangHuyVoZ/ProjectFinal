package com.example.projectfinal.model.signup


data class Signup(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: SignUpData,
    val success: Boolean
)