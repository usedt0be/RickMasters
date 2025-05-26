package com.example.rickmasters.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickmasters.R
import com.example.rickmasters.domain.models.CounterObservableValue
import com.example.rickmasters.domain.models.TrendDirection
import com.example.rickmasters.presentation.screens.statistics.components.PeriodSelector
import com.example.rickmasters.presentation.screens.statistics.components.UsersActivityDifferenceCounter
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod
import com.example.rickmasters.presentation.screens.statistics.entity.VisitorsPeriod
import com.example.rickmasters.presentation.screens.statistics.gender_age_diagram.CircleDiagram
import com.example.rickmasters.presentation.screens.statistics.gender_age_diagram.LinearDiagram
import com.example.rickmasters.presentation.screens.statistics.top_visitors.VisitorItem
import com.example.rickmasters.presentation.screens.statistics.visitors_diagram.VisitorsDiagram
import com.example.rickmasters.theme.AppTheme


@Composable
fun StatisticsScreen(
    factory: StatisticsViewModelFactory,
    statisticsViewModel: StatisticsViewModel = viewModel(factory = factory)
){
    val state = statisticsViewModel.state
    val favoriteVisitors= state.favoriteVisitors
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = AppTheme.colors.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 16.dp)
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
                .verticalScroll(state = scrollState)
        ) {
            Text(
                text = stringResource(R.string.statistic_title),
                modifier = Modifier.padding(top = 48.dp),
                style = AppTheme.typography.title,
                color = AppTheme.colors.textPrimary
            )

            Text(
                text = stringResource(R.string.visitors_subtitle),
                style = AppTheme.typography.subTitle,
                modifier = Modifier.padding(
                    top = 32.dp
                ),
                color = AppTheme.colors.textPrimary
            )

            UsersActivityDifferenceCounter(
                trendDirection = TrendDirection.INCREASE,
                visitorsDifference = state.users.count(),
                counterObservableValue = CounterObservableValue.VISITORS,
                modifier = Modifier.padding(
                    top = 12.dp, end = 16.dp
                ),
                shape = RoundedCornerShape(16.dp),
                color = AppTheme.colors.primaryContainer
            )

            PeriodSelector(
                periods = VisitorsPeriod.entries,
                onSelect = { period ->
                    statisticsViewModel.filterVisitorsByPeriod(
                        statistics = state.statistics,
                        visitorsPeriod = period as VisitorsPeriod
                        )
                },
                modifier = Modifier.padding(top = 28.dp,end = 16.dp)
            )

            VisitorsDiagram(
                statistics = state.visitorsDiagramData,
                modifier = Modifier.padding(top = 12.dp, end = 16.dp),
                lineColor = AppTheme.colors.secondary
            )

            Text(
                text = stringResource(R.string.most_visitors_subtitle),
                modifier = Modifier.padding(top = 28.dp, end = 16.dp),
                style = AppTheme.typography.subTitle,
                color = AppTheme.colors.textPrimary
            )

            Column(
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .background(
                        color = AppTheme.colors.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                favoriteVisitors.forEachIndexed { index, visitor ->
                    VisitorItem(
                        visitor = visitor
                    )
                    if(index != favoriteVisitors.lastIndex) {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 48.dp, end = 2.dp),
                            color = AppTheme.colors.onPrimaryContainer
                        )
                    }
                }
            }


            Text(
                text = stringResource(R.string.gender_age_subtitile),
                modifier = Modifier.padding(top = 28.dp),
                style = AppTheme.typography.subTitle,
                color = AppTheme.colors.textPrimary
            )

            PeriodSelector(
                periods = GenderAgeDiagramPeriod.entries,
                onSelect = { period ->
                    statisticsViewModel.filterGenderAgeByPeriod(
                        period = period as GenderAgeDiagramPeriod
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 16.dp)
                    .background(
                        color = AppTheme.colors.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                CircleDiagram(
                    items = state.genderAgeDiagramData,
                    category = {it.sex},
                    modifier = Modifier.padding(),
                    colors = listOf(
                        AppTheme.colors.secondary,
                        AppTheme.colors.tertiary
                    ),
                    labelMapping = mapOf(
                        "M" to stringResource(R.string.men_stat),
                        "W" to stringResource(R.string.woman_stat)
                    )
                )

                val ageGroups = listOf(
                    18..21,
                    22..25,
                    26..30,
                    31..35,
                    36..40,
                    41..50,
                    51..150
                )

                HorizontalDivider(
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                )

                LinearDiagram(
                    items = state.genderAgeDiagramData,
                    groups = ageGroups,
                    groupSelector = { user ->
                        ageGroups.firstOrNull { user.age in it }
                    },
                    categorySelector = { it.sex },
                    categories = listOf("M", "W"),
                    categoryColors = mapOf("M" to AppTheme.colors.secondary,
                        "W" to AppTheme.colors.tertiary),
                    groupLabel = {range ->
                        if (range.first == 51) ">50" else "${range.first}-${range.last}"
                    }
                )
            }

            Text(
                text = stringResource(R.string.subs_subtitle),
                style = AppTheme.typography.subTitle,
                color = AppTheme.colors.textPrimary,
                modifier = Modifier.padding(start = 16.dp, top = 28.dp, bottom = 12.dp)
            )


            UsersActivityDifferenceCounter(
                trendDirection = TrendDirection.INCREASE,
                counterObservableValue = CounterObservableValue.SUBSCRIPTIONS,
                visitorsDifference = state.statistics.count { it.type == "subscription" },
                modifier = Modifier.padding(end = 16.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = AppTheme.colors.primaryContainer
            )

            HorizontalDivider(
                thickness = 0.5.dp,
                modifier = Modifier
            )

            UsersActivityDifferenceCounter(
                trendDirection = TrendDirection.DECREASE,
                counterObservableValue = CounterObservableValue.SUBSCRIPTIONS,
                visitorsDifference = state.statistics.count { it.type == "unsubscription" },
                modifier = Modifier.padding(end = 16.dp, bottom = 32.dp),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                color = AppTheme.colors.primaryContainer
            )
        }
    }
}




