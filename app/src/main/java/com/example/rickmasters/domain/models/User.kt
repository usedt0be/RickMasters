package com.example.rickmasters.domain.models

data class User(
    val id: Int,
    val sex: String,
    val username: String,
    val isOnline: Boolean,
    val age: Int,
    val files: List<UserFile>
)