package com.example.projectfinal.model.post


import com.google.gson.annotations.SerializedName

data class CommentPost(
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
    @SerializedName("isUpdated")
    val isUpdated: Boolean,
    @SerializedName("postId")
    val postId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)