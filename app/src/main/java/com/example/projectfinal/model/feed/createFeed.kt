package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class createFeed(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: createFeedData,
    val success: Boolean
)