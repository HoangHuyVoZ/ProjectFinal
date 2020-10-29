package com.example.projectfinal.model.login


import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: LoginData,
    @SerializedName("success")
    val success: Boolean
)