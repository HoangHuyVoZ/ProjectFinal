package com.example.projectfinal.model.comment


import com.google.gson.annotations.SerializedName

data class comment(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<commentData>,
    val success: Boolean,
    val message: String,
)