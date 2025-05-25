package com.example.rickmasters.presentation.screens.statistics.gender_age_diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rickmasters.theme.AppTheme


@Composable
fun <T, V> LinearDiagram(
    items: List<T>,
    groups: List<V>,
    groupSelector: (T) -> V?,
    categorySelector: (T) -> String,
    categories: List<String>,
    categoryColors: Map<String, Color>,
    modifier: Modifier = Modifier,
    groupLabel: (V) -> String = { it.toString() },
) {

    val groupedItems: Map<V, List<T>> = items.mapNotNull { item ->
        val group = groupSelector(item)
        if (group != null) group to item else null
    }.groupBy({ it.first }, { it.second })

    val statsByGroup: Map<V, Map<String, Float>> = groupedItems.mapValues { (_, itemsInGroup) ->
        if (itemsInGroup.isEmpty()) {
            categories.associateWith { 0f }
        } else {
            val total = itemsInGroup.size.toFloat()
            val counts = itemsInGroup.groupingBy(categorySelector).eachCount()
            categories.associateWith { (counts[it] ?: 0) * 100f / total }
        }
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        groups.forEach { group ->
            val label = groupLabel(group)
            val percentages = statsByGroup[group] ?: categories.associateWith { 0f }
            LinearDiagramItem(
                label = label,
                metrics = categories.map { it to (percentages[it] ?: 0f) to (categoryColors[it] ?: Color.Gray) }
            )
        }
    }
}


@Composable
fun LinearDiagramItem(
    label: String,
    metrics: List<Pair<Pair<String, Float>, Color>>,
    modifier: Modifier = Modifier
) {


    Row(
        modifier = modifier
            .heightIn(min = 40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(68.dp)
                .padding(start = 25.dp),
            style = AppTheme.typography.bodyRegular,
            color = AppTheme.colors.textPrimary
        )


        Column(
            modifier = modifier.padding(start = 34.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            metrics.forEach { (data, color) ->
                val (category, percent) = data
                PercentageRow(
                    percentage = percent,
                    color = color
                )
            }
        }
    }
}



@Composable
fun PercentageRow(
    percentage: Float?,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    maxWidth: Dp = 100.dp,
    minWidth: Dp = 4.dp,
) {
    val barWidth = if ((percentage ?: 0f) > 0f) {
        maxWidth * ((percentage ?: 0f).coerceIn(0f, 100f) / 100f)
    } else {
        minWidth
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .width(barWidth)
                .height(4.dp)
        ) {
            val heightPx = size.height
            drawRoundRect(
                color = color,
                size = Size(width = size.width, height = heightPx),
                cornerRadius = CornerRadius(x = heightPx / 2, y = heightPx / 2)
            )
        }

        Text(
            text = "${percentage?.toInt() ?: 0}%",
            modifier = Modifier.align(Alignment.CenterVertically),
            style = AppTheme.typography.caption2,
            color = AppTheme.colors.textPrimary
        )
    }
}

