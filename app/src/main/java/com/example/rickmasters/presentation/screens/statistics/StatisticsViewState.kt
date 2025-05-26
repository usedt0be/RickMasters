package com.example.rickmasters.presentation.screens.statistics

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod
import com.example.rickmasters.presentation.screens.statistics.entity.VisitorsPeriod


data class StatisticsViewState(
    val users: List<User> = emptyList(),
    val statistics: List<Statistic> = emptyList(),
    val favoriteVisitors: List<User> = emptyList(),
    val visitorsPeriod: VisitorsPeriod = VisitorsPeriod.DAYS,
    val visitorsDiagramData: Map<String, Int> = emptyMap(),
    val genderAgePeriod: GenderAgeDiagramPeriod = GenderAgeDiagramPeriod.TODAY,
    val genderAgeDiagramData: List<User> = emptyList(),
)