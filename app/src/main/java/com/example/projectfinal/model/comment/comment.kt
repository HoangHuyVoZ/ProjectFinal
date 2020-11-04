package com.example.projectfinal.model.comment


import com.google.gson.annotations.SerializedName

data class comment(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: List<commentData>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
)