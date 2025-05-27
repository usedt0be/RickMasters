package com.example.rickmasters.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GetPreviousDaysUseCase() {
    enum class Period {
        DAYS,
        WEEKS,
        MONTHS
    }

    companion object {
        private const val DAYS_DEFAULT = 7
        private const val WEEKS_DEFAULT = 7
        private const val MONTHS_DEFAULT = 7
    }

    fun invoke(
        startDate: String,
        period: Period,
        days: Int = DAYS_DEFAULT,
        weeks: Int = WEEKS_DEFAULT,
        months: Int = MONTHS_DEFAULT,
    ): List<String> {
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        val date = formatter.parse(startDate)
            ?: return emptyList()

        val calendar = Calendar.getInstance().apply {
            time = date
            when(period) {
                Period.DAYS -> {
                    add(Calendar.DAY_OF_MONTH, -(days - 1))
                }
                Period.WEEKS -> {
                    add(Calendar.DAY_OF_MONTH, -6 * weeks)
                }
                Period.MONTHS -> {
                    set(Calendar.DAY_OF_MONTH, 1)
                    add(Calendar.MONTH, -(months - 1))
                }
            }
        }
        return when(period) {
            Period.DAYS -> {
                List(days) {
                    val formattedDate = formatter.format(calendar.time)
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    formattedDate
                }
            }
            Period.WEEKS -> {
                List(weeks) {
                    val formattedDate = formatter.format(calendar.time)
                    calendar.add(Calendar.DAY_OF_MONTH, 7)
                    formattedDate
                }
            }
            Period.MONTHS -> {
                List(months) {
                    val formattedDate = formatter.format(calendar.time)
                    calendar.add(Calendar.MONTH, 1)
                    formattedDate
                }
            }
        }
    }
}