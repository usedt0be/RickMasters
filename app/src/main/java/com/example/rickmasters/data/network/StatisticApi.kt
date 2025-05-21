package com.example.rickmasters.data.network

import com.example.rickmasters.data.network.response.StatisticsResponse
import com.example.rickmasters.data.network.response.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class StatisticApi(
    private val httpClient: HttpClient
) {
    suspend fun getUsers(): UsersResponse =
        httpClient.get("/api/users/").body<UsersResponse>()

    suspend fun getStatistics(): StatisticsResponse =
        httpClient.get("api/statistics/").body<StatisticsResponse>()

}