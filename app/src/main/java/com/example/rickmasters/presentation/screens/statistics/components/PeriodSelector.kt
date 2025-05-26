package com.example.rickmasters.presentation.screens.statistics.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rickmasters.presentation.screens.statistics.entity.SelectablePeriod
import com.example.rickmasters.theme.AppTheme


@Composable
fun PeriodSelector(
    periods: List<SelectablePeriod>,
    onSelect: (SelectablePeriod) -> Unit,
    modifier: Modifier = Modifier
){
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        periods.forEachIndexed { index, name ->
            val isSelected = index == selectedIndex
            Button(
                onClick = {
                    selectedIndex = index
                    onSelect(periods[index])
                },
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(percent = 50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) {
                        AppTheme.colors.secondary
                    } else {
                        Color.Transparent
                    }
                ),
                border = BorderStroke(
                    width = if (isSelected) { 0.dp } else {1.dp},
                    color = AppTheme.colors.textSecondary
                )
            ) {
                Text(
                    text = name.label,
                    modifier = Modifier,
                    color = if (isSelected) {
                        AppTheme.colors.primary
                    } else {
                        AppTheme.colors.textPrimary
                    },
                    style = AppTheme.typography.bodyRegular,
                    maxLines = 1
                )
            }
        }
    }
}