package com.example.projectfinal.model.group


import com.google.gson.annotations.SerializedName

data class GroupIdData(
    val createdAt: String,
    val createdBy: String,
    @SerializedName("_id")
    val id: String,
    val name: String
)