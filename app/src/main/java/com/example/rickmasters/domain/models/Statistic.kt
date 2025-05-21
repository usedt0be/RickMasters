package com.example.rickmasters.domain.models

data class Statistic(
    val userId: Int,
    val type: String,
    val dates: List<Int>
)
