package com.example.projectfinal.model.user


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("display_name")
    val displayName: String,
    val email: String,
    val gender: String,
    @SerializedName("_id")
    val id: String,
    val role: String
)