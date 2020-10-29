package com.example.projectfinal.model.Topic


import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error")
    val error: Any,
    @SerializedName("options")
    val options: Any,
    @SerializedName("result")
    val result: List<TopicData>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
val message: String,
)