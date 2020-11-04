package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("commentsPost")
    val commentsPost: List<CommentPost>,
    @SerializedName("countCommentPost")
    val countCommentPost: Int,
    @SerializedName("countLike")
    val countLike: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("title")
    val title: String
)