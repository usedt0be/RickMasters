package com.example.rickmasters.domain.interactor

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.usecase.GetLatestMonthDateUseCase
import com.example.rickmasters.domain.usecase.GetPreviousDaysUseCase
import com.example.rickmasters.domain.usecase.GetUniqueViewsCountUseCase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class VisitorsPeriodFilterInteractorImpl(
    private val getUniqueViewsCountUseCase: GetUniqueViewsCountUseCase,
    private val getPreviousDaysUseCase: GetPreviousDaysUseCase,
    private val getLatestMonthDateUseCase: GetLatestMonthDateUseCase,
): VisitorsPeriodFilterInteractor {
    override fun filterVisitorsByDays(statistics: List<Statistic>): Map<String, Int>? {
        val uniqueViews = getUniqueViewsCountUseCase.invoke(statistics)
        val dailyVisitors =
            getLatestMonthDateUseCase.invoke(uniqueViews.keys.toList())?.let { date ->
                getPreviousDaysUseCase.invoke(startDate = date, period = GetPreviousDaysUseCase.Period.DAYS).associateWith { date ->
                    uniqueViews[date] ?: 0
                }
            }
        return dailyVisitors
    }

    override fun filterVisitorsByWeeks(statistics: List<Statistic>): Map<String, Int>? {
        val uniqueViews = getUniqueViewsCountUseCase.invoke(statistics)
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        return getLatestMonthDateUseCase
            .invoke(uniqueViews.keys.toList())
            ?.let { latestDate ->
                getPreviousDaysUseCase
                    .invoke(startDate = latestDate, period = GetPreviousDaysUseCase.Period.WEEKS)
                    .associateWith { weekStartStr ->
                        val start = formatter.parse(weekStartStr) ?: return@associateWith 0
                        val end = Calendar.getInstance().apply {
                            time = start
                            add(Calendar.DAY_OF_MONTH, 6)
                        }.time

                        uniqueViews
                            .filterKeys { dateStr ->
                                val parsed = formatter.parse(dateStr)
                                parsed != null && !parsed.before(start) && !parsed.after(end)
                            }
                            .values
                            .sum()
                    }
            }

    }


    override fun filterVisitorsByMonths(statistics: List<Statistic>): Map<String, Int>? {
        val uniqueViews = getUniqueViewsCountUseCase.invoke(statistics)
        val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        return getLatestMonthDateUseCase
            .invoke(uniqueViews.keys.toList())
            ?.let { latestDate ->
                getPreviousDaysUseCase.invoke(startDate = latestDate, period = GetPreviousDaysUseCase.Period.MONTHS)
                    .associateWith { monthStartStr ->
                        val start = formatter.parse(monthStartStr) ?: return@associateWith 0
                        val calendar = Calendar.getInstance().apply {
                            time = start
                            add(Calendar.MONTH, 1)
                            add(Calendar.DAY_OF_MONTH, -1)
                        }
                        val end = calendar.time

                        uniqueViews
                            .filterKeys { dateStr ->
                                val parsed = formatter.parse(dateStr)
                                parsed != null && !parsed.before(start) && !parsed.after(end)
                            }
                            .values
                            .sum()
                    }
            }
    }


}
