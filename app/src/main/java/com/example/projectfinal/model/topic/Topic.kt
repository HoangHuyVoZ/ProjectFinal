package com.example.projectfinal.model.topic


data class Topic(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<TopicData>,
    val success: Boolean,
val message: String,
)