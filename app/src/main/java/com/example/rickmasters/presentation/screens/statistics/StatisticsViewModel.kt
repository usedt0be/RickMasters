package com.example.rickmasters.presentation.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmasters.domain.interactor.GenderAgePeriodFilterInteractor
import com.example.rickmasters.domain.interactor.VisitorsPeriodFilterInteractor
import com.example.rickmasters.domain.models.Statistic
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.domain.usecase.GetFavoriteVisitorsUseCase
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod
import com.example.rickmasters.presentation.screens.statistics.entity.VisitorsPeriod
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber


class StatisticsViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getStatisticUseCase: GetStatisticUseCase,
    private val getFavoriteVisitorsUseCase: GetFavoriteVisitorsUseCase,
    private val visitorsPeriodFilterInteractor: VisitorsPeriodFilterInteractor,
    private val genderAgePeriodFilterInteractor: GenderAgePeriodFilterInteractor,
): ViewModel() {

    var state by mutableStateOf(StatisticsViewState())
        private set

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag("CEH_ERROR").d("error: $throwable")
    }

    init {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val users = getUsersUseCase.invoke().first()
                val statistics = getStatisticUseCase.invoke().first()
                state = state.copy(users = users, statistics = statistics)
                val favoriteVisitors =
                    getFavoriteVisitorsUseCase.invoke(statistics = statistics, users = users)
                state = state.copy(favoriteVisitors = favoriteVisitors)
                filterVisitorsByPeriod(statistics, visitorsPeriod = state.visitorsPeriod)
                filterGenderAgeByPeriod(period = state.genderAgePeriod)
            } catch (e: CancellationException) {
                e.printStackTrace()
            }
        }
    }

    fun filterVisitorsByPeriod(statistics: List<Statistic>,  visitorsPeriod: VisitorsPeriod) {
        when(visitorsPeriod) {
            VisitorsPeriod.DAYS -> {
                visitorsPeriodFilterInteractor.filterVisitorsByDays(statistics)?.let {
                    state = state.copy(visitorsDiagramData = it, visitorsPeriod = visitorsPeriod)
                }
            }
            VisitorsPeriod.WEEKS -> {
                visitorsPeriodFilterInteractor.filterVisitorsByWeeks(statistics = statistics)?.let {
                    state = state.copy(visitorsDiagramData = it, visitorsPeriod = visitorsPeriod)
                }
            }
            VisitorsPeriod.MONTHS -> {
                visitorsPeriodFilterInteractor.filterVisitorsByMonths(statistics)?.let {
                    state = state.copy(visitorsDiagramData = it, visitorsPeriod = visitorsPeriod)
                }
            }
        }
    }

    fun filterGenderAgeByPeriod(
        statistics: List<Statistic> = state.statistics,
        users: List<User> = state.users,
        period: GenderAgeDiagramPeriod
    ) {
        genderAgePeriodFilterInteractor.filterGenderAge(
            statistics = statistics,
            users = users,
            period = period
        ).let {
            state = state.copy(
                genderAgePeriod = period,
                genderAgeDiagramData = it
            )
        }
    }

}



