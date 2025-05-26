package com.example.rickmasters.domain.usecase

class GetLatestMonthDateUseCase {
    fun invoke(dates: List<String>): String? {
        return dates.maxByOrNull { date ->
            val parts = date.split(".")
            val day = parts.getOrNull(0)?.toIntOrNull() ?: 0
            val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
            month * 100 + day
        }
    }
}
