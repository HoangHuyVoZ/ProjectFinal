package com.example.projectfinal.model.comment



data class CreateComment(

    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: CreateCommentData,
    val success: Boolean
)