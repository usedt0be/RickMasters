package com.example.rickmasters.presentation

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun parseToDayMonthManual(input: String): String? {
        if (input.length < 6 || input.length > 8) return null
        val dayLength = input.length - 6
        val day = input.substring(0, dayLength).padStart(2, '0')
        val month = input.substring(dayLength, dayLength + 2).padStart(2, '0')
        return "$day.$month"
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

}