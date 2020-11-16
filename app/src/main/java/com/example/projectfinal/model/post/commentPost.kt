package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class CommentPost(
    val countLike: Int,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    @SerializedName("_id")
    val id: String,
    val isUpdated: Boolean,
    val postId: String,
    val status: String,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)