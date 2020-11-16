package com.example.projectfinal.model.group


data class Group(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<Groupdata>,
    val success: Boolean,
    val message: String,
)