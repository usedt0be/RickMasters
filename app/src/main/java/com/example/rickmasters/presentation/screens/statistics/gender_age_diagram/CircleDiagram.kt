package com.example.rickmasters.presentation.screens.statistics.gender_age_diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rickmasters.theme.AppTheme


@Composable
fun <T> CircleDiagram(
    items: List<T>,
    category:(T) -> String,
    modifier: Modifier = Modifier,
    colors: List<Color> = emptyList(),
    labelMapping: Map<String, String> = emptyMap(),
    gapAngle: Float = 12f,
    arcThickness: Dp = 12.dp,
) {
    val groups: Map<String, Int> = items.groupingBy(category).eachCount()
    val total = groups.values.sum().toFloat()
    val values = groups.values.map { it / total }
    val labels = groups.keys.toList().map { key ->
        labelMapping[key] ?: key
    }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .size(150.dp)
                    .rotate(gapAngle/values.size)
            ) {
                var startAngle = 90f
                val diameter = size.minDimension
                val stroke = arcThickness.toPx()
                var currentStartAngle = startAngle

                values.forEachIndexed { index, value ->
                    val sweepAngle = value * 360f - gapAngle
                    drawArc(
                        color = colors.getOrElse(index) {Color.Gray},
                        startAngle = currentStartAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        size = Size(diameter, diameter),
                        style = Stroke(width = stroke, cap = StrokeCap.Round),
                        topLeft = Offset.Zero
                    )
                    currentStartAngle += sweepAngle + gapAngle
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                labels.forEachIndexed { index, value->
                    val percent = values.get(index)
                    LegendDiagramItem(
                        label = value,
                        percent = percent,
                        color = colors.getOrElse(index = index, defaultValue = {Color.Gray})
                    )
                }
            }
    }
}




@Composable
fun LegendDiagramItem(
    label: String,
    percent: Float,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(color)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label ${"%.0f".format(percent * 100)}%",
            modifier = Modifier,
            style = AppTheme.typography.caption1,
            color = AppTheme.colors.textPrimary
        )
    }
}