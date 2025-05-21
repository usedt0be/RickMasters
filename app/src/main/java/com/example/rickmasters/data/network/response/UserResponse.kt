package com.example.rickmasters.data.network.response

import com.example.rickmasters.domain.models.User
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse(
    val id: Int,
    val sex: String,
    val username: String,
    val isOnline: Boolean,
    val age: Int,
    val files: List<UserFileResponse>
) {
    fun toUser() = User(
        id = id,
        sex = sex,
        username = username,
        isOnline = isOnline,
        age = age,
        files = files.map { it.toUserFile() }
    )
}