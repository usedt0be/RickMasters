package com.example.rickmasters.presentation.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class StatisticsViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getStatisticUseCase: GetStatisticUseCase,
): ViewModel() {

    var state by mutableStateOf(StatisticsViewState())
        private set

    init {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val users = async { getUsersUseCase.invoke() }.await()
                val statistics = async { getStatisticUseCase.invoke() }.await()
                state= state.copy(
                    users = users,
                    statistics = statistics
                )
            }
        } catch (e: CancellationException) {

        }


    }

}



