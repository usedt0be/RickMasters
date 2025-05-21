package com.example.rickmasters.data.network.response

import kotlinx.serialization.Serializable


@Serializable
data class StatisticsResponse(
    val statistics: List<StatisticResponse>
)