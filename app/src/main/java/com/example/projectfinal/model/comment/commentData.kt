package com.example.projectfinal.model.comment


import com.google.gson.annotations.SerializedName

data class commentData(
    val countLike: Int,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val flags: List<Any>,
    val id: String,
    val isUpdated: Boolean,
    val postId: String,
    val status: String,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)