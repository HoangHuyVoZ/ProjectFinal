package com.example.projectfinal.model.Group


import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: List<Groupdata>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
)