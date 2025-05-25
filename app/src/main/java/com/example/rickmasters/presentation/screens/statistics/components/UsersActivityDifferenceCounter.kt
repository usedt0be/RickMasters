package com.example.rickmasters.presentation.screens.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.rickmasters.R
import com.example.rickmasters.domain.models.CounterObservableValue
import com.example.rickmasters.domain.models.TrendDirection
import com.example.rickmasters.theme.AppTheme


@Composable
fun UsersActivityDifferenceCounter(
    trendDirection: TrendDirection,
    counterObservableValue: CounterObservableValue,
    visitorsDifference: Int,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape
) {
    Surface(
        modifier = modifier.fillMaxWidth()
            .heightIn(min = 96.dp),
        shape = shape,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                imageVector = if (trendDirection == TrendDirection.INCREASE) {
                    ImageVector.vectorResource(R.drawable.ic_grown_chart)
                } else {
                    ImageVector.vectorResource(R.drawable.ic_fall_chart)
                },
                contentDescription = null,
                modifier = Modifier.padding(
                    start = 20.dp
                ),
                tint = Color.Unspecified
            )

            Column(
                modifier = Modifier.padding(
                    end = 20.dp
                ),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = visitorsDifference.toString(),
                        modifier = Modifier,
                        style = AppTheme.typography.subTitle,
                        color = AppTheme.colors.textPrimary
                    )

                    Icon(
                        imageVector = if (trendDirection == TrendDirection.INCREASE) {
                            ImageVector.vectorResource(R.drawable.ic_arrow_up)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_arrow_down)
                        },
                        contentDescription = null,
                        modifier = Modifier,
                        tint = Color.Unspecified
                    )
                }

                Text(
                    text = stringResource(
                        id = if (trendDirection == TrendDirection.INCREASE) {
                            counterObservableValue.increaseRes
                        } else {
                            counterObservableValue.decreaseRes
                        }
                    ),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textSecondary,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }

}



@Composable
@Preview
fun VisitorsDifferenceCounterPreview(){
    UsersActivityDifferenceCounter(
        trendDirection = TrendDirection.INCREASE,
        counterObservableValue = CounterObservableValue.VISITORS,
        visitorsDifference = 800,
    )
}