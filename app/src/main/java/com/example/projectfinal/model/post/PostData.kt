package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class PostData(
    val commentsPost: List<CommentPost>,
    val countCommentPost: Int,
    val countLike: Int,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    @SerializedName("_id")
    val id: String,
    val title: String
)