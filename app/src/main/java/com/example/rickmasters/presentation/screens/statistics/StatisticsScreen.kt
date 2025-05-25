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
import com.example.rickmasters.domain.models.TrendDirection
import com.example.rickmasters.presentation.Utils
import com.example.rickmasters.presentation.screens.statistics.components.PeriodSelector
import com.example.rickmasters.presentation.screens.statistics.components.UsersActivityDifferenceCounter
import com.example.rickmasters.presentation.screens.statistics.entity.GenderAgeDiagramPeriod
import com.example.rickmasters.presentation.screens.statistics.entity.VisitorsPeriod
import com.example.rickmasters.presentation.screens.statistics.gender_age_diagram.CircleDiagram
import com.example.rickmasters.presentation.screens.statistics.gender_age_diagram.LinearDiagram
import com.example.rickmasters.presentation.screens.statistics.top_visitors.VisitorItem
import com.example.rickmasters.presentation.screens.statistics.visitors_diagram.VisitorsDiagram
import com.example.rickmasters.theme.AppTheme
import timber.log.Timber


@Composable
fun StatisticsScreen(
    factory: StatisticsViewModelFactory,
    statisticsViewModel: StatisticsViewModel = viewModel(factory = factory)
){

    val state = statisticsViewModel.state
    val uniqueViews = Utils.getDailyUniqueViewCounts(state.statistics)
    val minimalDate = Utils.findEarliestDayMonth(uniqueViews.keys.toList())
    val week = minimalDate?.let { Utils.getWeekFrom(it) }

    val visitorsToWeek = week?.associateWith { date ->
        uniqueViews[date]?:0
    } ?: emptyMap()
    val favoriteVisitors= Utils.getFavoriteUsers(state.statistics, state.users)

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = AppTheme.colors.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    start = 16.dp
                )
                .fillMaxSize()
                .background(color = AppTheme.colors.primary)
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
                    top = 32.dp
                ),
                color = AppTheme.colors.textPrimary
            )

            UsersActivityDifferenceCounter(
                trendDirection = TrendDirection.INCREASE,
                visitorsDifference = 8,
                modifier = Modifier.padding(
                    top = 12.dp, end = 16.dp
                )
            )

            PeriodSelector(
                periods = VisitorsPeriod.entries,
                onSelect = {},
                modifier = Modifier.padding(top = 28.dp,end = 16.dp)
            )

            VisitorsDiagram(
                statistics = visitorsToWeek,
                modifier = Modifier.padding(top = 12.dp, end = 16.dp),
                lineColor = AppTheme.colors.secondary
            )

            Text(
                text = "Чаще всех посещают Ваш профиль",
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
                text = "Пол и возраст",
                modifier = Modifier.padding(top = 28.dp),
                style = AppTheme.typography.subTitle,
                color = AppTheme.colors.textPrimary
            )

            PeriodSelector(
                periods = GenderAgeDiagramPeriod.entries,
                onSelect = {
                    it
                    Timber.tag("PERIOD").d("$it")
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
                    items = state.users,
                    category = {it.sex},
                    modifier = Modifier.padding(),
                    colors = listOf(
                        AppTheme.colors.secondary,
                        AppTheme.colors.tertiary
                    ),
                    labelMapping = mapOf(
                        "M" to "Мужчины",
                        "W" to "Женщины"
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
                    items = state.users,
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
        }
    }
}




