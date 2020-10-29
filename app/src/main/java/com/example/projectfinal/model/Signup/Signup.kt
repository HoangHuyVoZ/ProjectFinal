package com.example.projectfinal.model.Signup


import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: SignUpData,
    @SerializedName("success")
    val success: Boolean
)