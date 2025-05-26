package com.example.rickmasters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.RikMastersApp
import com.example.rickmasters.presentation.screens.statistics.StatisticsScreen
import com.example.rickmasters.presentation.screens.statistics.StatisticsViewModelFactory
import com.example.rickmasters.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (applicationContext as RikMastersApp).appContainer
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val factory = StatisticsViewModelFactory(
                    getStatisticUseCase =  appContainer.statisticUseCase,
                    getUsersUseCase = appContainer.getUserUseCase,
                    getFavoriteVisitorsUseCase = appContainer.getFavoriteUsersUseCase,
                    visitorsPeriodFilterInteractor = appContainer.visitorsPeriodFilterInteractor,
                    genderAgePeriodFilterInteractor = appContainer.genderAgePeriodInteractor
                )
                StatisticsScreen(factory = factory)
            }
        }
    }
}

