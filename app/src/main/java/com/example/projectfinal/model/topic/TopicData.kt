package com.example.projectfinal.model.topic


import com.google.gson.annotations.SerializedName

data class TopicData(
    val createdAt: String,
    val createdBy: String,
    val description: String,
    @SerializedName("_id")
    val id: String,
    val name: String
)