package com.example.rickmasters.data.repository


import com.example.rickmasters.data.network.StatisticApi
import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.domain.repository.StatisticRepository

class StatisticRepositoryImpl(
    private val statisticApi: StatisticApi
): StatisticRepository {

    override suspend fun getUsers(): List<User> =
       statisticApi.getUsers().users.map { it.toUser() }


    override suspend fun getStatistic(): List<Statistic> =
       statisticApi.getStatistics().statistics.map { it.toStatistic() }

}