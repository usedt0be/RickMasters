package com.example.rickmasters.presentation

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
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

    fun parseDate(input: String): String {
        val inputFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM", Locale("ru"))

        val date = inputFormat.parse(input)
        return outputFormat.format(date!!)
    }

    fun pluralizeVisitors(count: Int): String {
        val remainder10 = count % 10
        val remainder100 = count % 100

        val word = when {
            remainder100 in 11..14 -> "посетителей"
            remainder10 == 1 -> "посетитель"
            remainder10 in 2..4 -> "посетителя"
            else -> "посетителей"
        }

        return "$count $word"
    }

    fun getFavoriteUsers(statistics: List<Statistic>, users: List<User>): List<User> {
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