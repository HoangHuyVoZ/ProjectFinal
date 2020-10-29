package com.example.projectfinal.model.Signup


import com.google.gson.annotations.SerializedName

data class SignUpData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("id")
    val id: String
)