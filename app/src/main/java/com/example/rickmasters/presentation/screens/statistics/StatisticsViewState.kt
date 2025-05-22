package com.example.rickmasters.presentation.screens.statistics

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User

data class StatisticsViewState(
    val users: List<User> = emptyList(),
    val statistics: List<Statistic> = emptyList(),
)