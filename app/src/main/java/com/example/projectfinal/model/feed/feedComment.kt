package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class feedComment(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<feedCommentData>,
    val success: Boolean,
    val message: String,
)