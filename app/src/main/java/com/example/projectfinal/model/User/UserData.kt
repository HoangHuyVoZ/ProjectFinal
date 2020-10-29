package com.example.projectfinal.model.User


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("role")
    val role: String
)