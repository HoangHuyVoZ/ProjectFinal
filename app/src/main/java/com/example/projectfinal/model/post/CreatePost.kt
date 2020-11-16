package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class CreatePost(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: CreatePostData,
    val success: Boolean
)