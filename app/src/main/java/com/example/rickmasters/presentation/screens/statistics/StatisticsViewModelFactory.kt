package com.example.rickmasters.presentation.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickmasters.domain.interactor.GenderAgePeriodFilterInteractor
import com.example.rickmasters.domain.interactor.VisitorsPeriodFilterInteractorImpl
import com.example.rickmasters.domain.usecase.GetFavoriteVisitorsUseCase
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase

class StatisticsViewModelFactory(
    private val getUsersUseCase: GetUsersUseCase,
    private val getStatisticUseCase: GetStatisticUseCase,
    private val getFavoriteVisitorsUseCase: GetFavoriteVisitorsUseCase,
    private val visitorsPeriodFilterInteractor: VisitorsPeriodFilterInteractorImpl,
    private val genderAgePeriodFilterInteractor: GenderAgePeriodFilterInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(
                getUsersUseCase,
                getStatisticUseCase,
                getFavoriteVisitorsUseCase,
                visitorsPeriodFilterInteractor,
                genderAgePeriodFilterInteractor
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



