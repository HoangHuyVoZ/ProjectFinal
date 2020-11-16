package com.example.projectfinal.model.topic


data class topicId(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<topicIdData>,
    val success: Boolean
)