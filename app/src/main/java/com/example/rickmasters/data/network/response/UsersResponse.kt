package com.example.rickmasters.data.network.response

import kotlinx.serialization.Serializable


@Serializable
data class UsersResponse(
    val users: List<UserResponse>
)
