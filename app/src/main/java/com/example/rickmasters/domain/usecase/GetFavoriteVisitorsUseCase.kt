package com.example.rickmasters.domain.usecase

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User

class GetFavoriteVisitorsUseCase() {
    fun invoke(statistics: List<Statistic>, users: List<User>): List<User> {
        val viewsMap = statistics.filter { it.type == "view" }
            .groupBy { it.userId }
            .mapValues { (_, stats) ->
                stats.sumOf { it.dates.size }
            }

        val list = users.filter { viewsMap.containsKey(it.id) }
            .sortedByDescending { viewsMap[it.id] ?: 0 }
            .take(3)

        return list
    }
}