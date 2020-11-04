package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: List<PostData>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
val message: String,
)