package com.example.rickmasters.data.network.response

import com.example.rickmasters.domain.models.Statistic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatisticResponse(
    @SerialName("user_id")
    val userId: Int,
    val type: String,
    val dates: List<Int>
) {
    fun toStatistic() = Statistic(
        userId = userId,
        type = type,
        dates = dates
    )
}

