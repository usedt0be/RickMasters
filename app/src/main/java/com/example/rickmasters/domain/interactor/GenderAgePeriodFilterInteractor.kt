package com.example.rickmasters.domain.interactor

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod

interface GenderAgePeriodFilterInteractor {
    fun filterGenderAge(
        statistics: List<Statistic>,
        users: List<User>,
        period: GenderAgeDiagramPeriod
    ): List<User>

    fun filterByToday(usersMap: Map<User, List<String>>): List<User>

    fun filterByWeek(usersMap: Map<User, List<String>>): List<User>

    fun filterByMonth(usersMap: Map<User, List<String>>): List<User>
}