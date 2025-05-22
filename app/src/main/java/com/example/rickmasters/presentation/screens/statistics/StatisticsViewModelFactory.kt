package com.example.rickmasters.presentation.screens.statistics

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase

class StatisticsViewModelFactory(
    private val getUsersUseCase: GetUsersUseCase,
    private val getStatisticUseCase: GetStatisticUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(getUsersUseCase, getStatisticUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



