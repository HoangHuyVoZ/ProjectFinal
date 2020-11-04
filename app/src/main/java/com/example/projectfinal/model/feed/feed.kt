package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class feed(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: List<feedData>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
)