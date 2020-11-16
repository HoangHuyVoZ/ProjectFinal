package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class feedData(
    val attachments: List<String>,
    val avatar: String,
    val commentsFeed: List<Any>,
    val countCommentFeed: Int,
    val countLike: Int,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val flags: List<String>,
    @SerializedName("_id")
    val id: String,
    val userId: String
)