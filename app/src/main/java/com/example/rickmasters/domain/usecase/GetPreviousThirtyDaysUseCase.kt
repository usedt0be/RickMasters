package com.example.rickmasters.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetPreviousThirtyDaysUseCase() {
    fun invoke(startDate: String): List<String> {
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        val date = formatter.parse(startDate) ?: return emptyList()

        val calendar = Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, -29)
        }

        return List(30) {
            val formattedDate = formatter.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            formattedDate
        }
    }
}