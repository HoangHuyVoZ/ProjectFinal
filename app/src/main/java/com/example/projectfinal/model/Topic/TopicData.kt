package com.example.projectfinal.model.Topic


import com.google.gson.annotations.SerializedName

data class TopicData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)