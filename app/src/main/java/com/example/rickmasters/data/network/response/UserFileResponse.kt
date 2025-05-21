package com.example.rickmasters.data.network.response

import com.example.rickmasters.domain.models.UserFile
import kotlinx.serialization.Serializable


@Serializable
data class UserFileResponse(
    val id: Int,
    val url: String,
    val type: String
) {
    fun toUserFile() = UserFile(
        id = id,
        url = url,
        type = type
    )
}
