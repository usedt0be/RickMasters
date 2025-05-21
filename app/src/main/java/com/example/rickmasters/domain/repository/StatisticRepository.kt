package com.example.rickmasters.domain.repository

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User

interface StatisticRepository {
    suspend fun getUsers(): List<User>

    suspend fun getStatistic(): List<Statistic>
}