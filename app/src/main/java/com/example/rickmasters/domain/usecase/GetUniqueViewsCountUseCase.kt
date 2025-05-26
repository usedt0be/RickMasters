package com.example.rickmasters.domain.usecase

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.presentation.Utils.parseToDayMonthManual

class GetUniqueViewsCountUseCase() {

    fun invoke(statistics: List<Statistic>): Map<String, Int> {
        return statistics
            .asSequence()
            .filter { it.type == "view" }
            .flatMap { entry ->
                entry.dates.mapNotNull { date->
                    parseToDayMonthManual(date.toString())?.let { parsed ->
                        parsed to entry.userId
                    }
                }
            }
            .groupBy({it.first}, {it.second})
            .mapValues { (_, userIds) -> userIds.toSet().size }
    }

}