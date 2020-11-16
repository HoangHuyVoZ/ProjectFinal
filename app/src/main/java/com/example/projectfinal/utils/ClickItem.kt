package com.example.projectfinal.utils

interface ClickItem {
    fun onClickItem(id: String,role: Int,name: String,description: String,position: Int)
    fun onRemoveClick(position: Int,id: String)
}