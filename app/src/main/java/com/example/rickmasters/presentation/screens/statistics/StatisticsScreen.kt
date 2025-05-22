package com.example.rickmasters.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickmasters.R
import com.example.rickmasters.domain.models.TrendDirection
import com.example.rickmasters.presentation.Utils
import com.example.rickmasters.presentation.screens.statistics.components.VisitorsDifferenceCounter
import com.example.rickmasters.presentation.screens.statistics.visitors_diagram.VisitorsDiagram
import com.example.rickmasters.theme.AppTheme


@Composable
fun StatisticsScreen(
    factory: StatisticsViewModelFactory,
    statisticsViewModel: StatisticsViewModel = viewModel(factory = factory)
){

    val state = statisticsViewModel.state
    val users = state.users
    val statistics = state.statistics

    val uniqueViews = Utils.getDailyUniqueViewCounts(statistics)
    val minimalDate = Utils.findEarliestDayMonth(uniqueViews.keys.toList())
    val week = minimalDate?.let { Utils.getWeekFrom(it) }

    val visitorsToWeek = week?.associateWith { date ->
        uniqueViews[date]?:0
    } ?: emptyMap()


    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = AppTheme.colors.tertiary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .background(color = AppTheme.colors.tertiary)
                .verticalScroll(state = scrollState)
        ) {
            Text(
                text = stringResource(R.string.statistic_title),
                modifier = Modifier.padding(
                    top = 48.dp
                ),
                style = AppTheme.typography.title,
                color = AppTheme.colors.textPrimary
            )

            Text(
                text = stringResource(R.string.visitors_subtitle),
                style = AppTheme.typography.subTitle,
                modifier = Modifier.padding(
                    top = 32.dp,
                ),
                color = AppTheme.colors.textPrimary
            )

            VisitorsDifferenceCounter(
                trendDirection = TrendDirection.INCREASE,
                visitorsDifference = 8,
                modifier = Modifier.padding(
                    top = 12.dp
                )
            )

            VisitorsDiagram(
                statistics = visitorsToWeek,
                modifier = Modifier.padding(top = 28.dp),
                lineColor = AppTheme.colors.secondary
            )
        }
    }
}




