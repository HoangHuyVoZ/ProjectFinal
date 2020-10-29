package com.example.projectfinal.model.User


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: UserData,
    @SerializedName("success")
    val success: Boolean
)