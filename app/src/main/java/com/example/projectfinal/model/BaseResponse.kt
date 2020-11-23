package com.example.projectfinal.model


data class BaseResponse<An>(
    val code: Int = -1,
    val error: Any = "",
    val options: Any = "",
    val result: An,
    var success: Boolean = false,
    var message: String? = "",
)

data class BaseResponseList<T>(
    val code: Int,
    val error: Any,
    val options: Any,
    val result: List<T>,
    val success: Boolean,
    val message: String,
) {
}