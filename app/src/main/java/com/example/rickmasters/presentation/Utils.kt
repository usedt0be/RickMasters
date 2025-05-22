package com.example.rickmasters.presentation

import com.example.rickmasters.domain.models.Statistic
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun getDailyUniqueViewCounts(statistics: List<Statistic>): Map<String, Int> {
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

    fun parseToDayMonthManual(input: String): String? {
        if (input.length < 6 || input.length > 8) return null
        val dayLength = input.length - 6

        val day = input.substring(0, dayLength).padStart(2, '0')
        val month = input.substring(dayLength, dayLength + 2).padStart(2, '0')

        return "$day.$month"
    }


    fun findEarliestDayMonth(dates: List<String>): String? {
        return dates.minByOrNull { date ->
            val parts = date.split(".")
            val day = parts.getOrNull(0)?.toIntOrNull() ?: 0
            val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
            month * 100 + day
        }
    }

    fun getWeekFrom(startDate: String): List<String> {
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        val date = formatter.parse(startDate)
            ?: return emptyList()

        val calendar = Calendar.getInstance().apply {
            time = date
        }

        return List(7) {
            val formattedDate = formatter.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            formattedDate
        }
    }
}