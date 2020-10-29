package com.example.projectfinal.model.Group


import com.google.gson.annotations.SerializedName

data class Groupdata(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)