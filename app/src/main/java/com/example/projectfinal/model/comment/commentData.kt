package com.example.projectfinal.model.comment


import com.google.gson.annotations.SerializedName

data class commentData(
    @SerializedName("countLike")
    val countLike: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("flags")
    val flags: List<Any>,
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