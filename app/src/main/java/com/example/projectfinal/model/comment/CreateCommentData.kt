package com.example.projectfinal.model.comment



data class CreateCommentData(
    val createdAt: String,
    val createdBy: String,
    val id: String,
    val userId: String
)