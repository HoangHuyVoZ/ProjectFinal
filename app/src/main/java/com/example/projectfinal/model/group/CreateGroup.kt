package com.example.projectfinal.model.group


data class CreateGroup(
    val code: Int,
    val error: Any,
    val message: String,
    val options: Any,
    val result: CreateGroupData,
    val success: Boolean
)