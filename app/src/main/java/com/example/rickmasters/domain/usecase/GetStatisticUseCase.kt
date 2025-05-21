package com.example.rickmasters.domain.usecase

import com.example.rickmasters.domain.repository.StatisticRepository

class GetStatisticUseCase(
    private val statisticRepository: StatisticRepository
) {
    suspend operator fun invoke() = statisticRepository.getStatistic()
}