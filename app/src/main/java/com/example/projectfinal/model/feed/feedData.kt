package com.example.projectfinal.model.feed


import com.google.gson.annotations.SerializedName

data class feedData(
    @SerializedName("attachments")
    val attachments: List<String>,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("commentsFeed")
    val commentsFeed: List<Any>,
    @SerializedName("countCommentFeed")
    val countCommentFeed: Int,
    @SerializedName("countLike")
    val countLike: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("flags")
    val flags: List<String>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("userId")
    val userId: String
)