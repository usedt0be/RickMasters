package com.example.rickmasters.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetPreviousSevenDaysUseCase() {
    fun invoke(startDate: String): List<String> {
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        val date = formatter.parse(startDate)
            ?: return emptyList()

        val calendar = Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, -6)
        }

        return List(7) {
            val formattedDate = formatter.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            formattedDate
        }
    }
}