package com.example.rickmasters.domain.usecase

import com.example.rickmasters.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.flowOf

class GetUsersUseCase(
    private val statisticRepository: StatisticRepository
) {
    suspend operator fun invoke() = flowOf(statisticRepository.getUsers())
}