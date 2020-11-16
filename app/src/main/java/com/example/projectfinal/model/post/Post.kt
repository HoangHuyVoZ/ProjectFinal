package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class Post(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<PostData>,
    val success: Boolean,
val message: String,
)