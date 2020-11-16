package com.example.projectfinal.model.topic


data class CreateTopic(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: CreateTopicData,
    val success: Boolean
)