package com.example.projectfinal.model.group


data class groupID(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<GroupIdData>,
    val success: Boolean
)