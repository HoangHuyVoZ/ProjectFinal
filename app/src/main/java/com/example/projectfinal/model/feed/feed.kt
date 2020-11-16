package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class feed(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<feedData>,
    val success: Boolean,
    val message: String,
)