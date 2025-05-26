package com.example.rickmasters.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetPreviousSevenMonthsUseCase() {
    fun invoke(startDate: String): List<String> {
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())
        val date = formatter.parse(startDate) ?: return emptyList()

        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MONTH, -6)
        }

        return List(7) {
            val monthStart = formatter.format(calendar.time)
            calendar.add(Calendar.MONTH, 1)
            monthStart
        }
    }
}