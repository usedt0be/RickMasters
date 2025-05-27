package com.example.rickmasters.domain.interactor

import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.domain.parseTimestampToDate
import com.example.rickmasters.domain.usecase.GetLatestMonthDateUseCase
import com.example.rickmasters.domain.usecase.GetPreviousDaysUseCase
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod

class GenderAgePeriodFilterInteractorImpl(
    private val getLatestMonthDateUseCase: GetLatestMonthDateUseCase,
    private val getPreviousDaysUseCase: GetPreviousDaysUseCase,
): GenderAgePeriodFilterInteractor {

    override fun filterGenderAge(
        statistics: List<Statistic>,
        users: List<User>,
        period: GenderAgeDiagramPeriod
    ): List<User> {
        val userToDatesMap: Map<User, List<String>> = users.associateWith { user ->
            statistics
                .filter { it.userId == user.id && it.type == "view" }
                .flatMap {
                    it.dates.mapNotNull { timestamp ->
                        parseTimestampToDate(input = timestamp.toString())
                    }
                }
        }
        return when(period){
            GenderAgeDiagramPeriod.TODAY -> {
                filterByToday(usersMap = userToDatesMap)
            }
            GenderAgeDiagramPeriod.WEEK -> {
                filterByWeek(usersMap = userToDatesMap)
            }
            GenderAgeDiagramPeriod.MONTH -> {
                filterByMonth(usersMap = userToDatesMap)
            }
            GenderAgeDiagramPeriod.ALL_TIME -> {
                users
            }
        }
    }

    override fun filterByToday(usersMap: Map<User, List<String>>): List<User> {
        val maxDate = usersMap.values
            .mapNotNull { getLatestMonthDateUseCase.invoke(it) }
            .maxWithOrNull(compareBy { date ->
                val parts = date.split(".")
                val day = parts.getOrNull(0)?.toIntOrNull() ?: 0
                val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
                month * 100 + day
            }) ?: return emptyList()
        return usersMap.filter { (_, dates) ->
            getLatestMonthDateUseCase.invoke(dates) == maxDate
        }.keys.toList()
    }

    override fun filterByMonth(usersMap:Map<User, List<String>>): List<User> {
        val globalMaxDate = usersMap.values
            .mapNotNull { getLatestMonthDateUseCase.invoke(it) }
            .maxWithOrNull(compareBy { date ->
                val parts = date.split(".")
                val day = parts.getOrNull(0)?.toIntOrNull() ?: 0
                val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
                month * 100 + day
            }) ?: return emptyList()

        val lastMonthDates = getPreviousDaysUseCase.invoke(
            startDate = globalMaxDate,
            days = 30,
            period = GetPreviousDaysUseCase.Period.DAYS
        )

        return usersMap.filter { (_, dates) ->
            val maxDate = getLatestMonthDateUseCase.invoke(dates) ?: return@filter false
            maxDate in lastMonthDates
        }.keys.toList()
    }


    override fun filterByWeek(usersMap:Map<User, List<String>>): List<User> {
        val globalMaxDate = usersMap.values
            .mapNotNull { getLatestMonthDateUseCase.invoke(it) }
            .maxWithOrNull(compareBy { date ->
                val parts = date.split(".")
                val day = parts.getOrNull(0)?.toIntOrNull() ?: 0
                val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
                month * 100 + day
            }) ?: return emptyList()

        val lastWeekDates = getPreviousDaysUseCase.invoke(
            globalMaxDate,
            GetPreviousDaysUseCase.Period.WEEKS
        ).toSet()

        return usersMap.filter { (_, dates) ->
            val maxDate = getLatestMonthDateUseCase.invoke(dates) ?: return@filter false
            maxDate in lastWeekDates
        }.keys.toList()
    }

}