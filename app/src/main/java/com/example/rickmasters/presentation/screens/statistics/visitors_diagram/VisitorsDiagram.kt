package com.example.rickmasters.presentation.screens.statistics.visitors_diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickmasters.R
import com.example.rickmasters.presentation.Utils
import com.example.rickmasters.theme.AppTheme


@Composable
fun VisitorsDiagram(
    statistics: Map<String, Int>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.colors.primaryContainer,
    lineColor: Color = AppTheme.colors.secondary,
    gridColor: Color = AppTheme.colors.textSecondary,
    gridStep: Int = 5,
    maxGridValue: Int = 10
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    var selectedPoint by remember { mutableStateOf<Pair<String, Offset>?>(null) }
    var ptsState by remember { mutableStateOf<List<Pair<String, Offset>>>(emptyList()) }
    val clickRadiusPx = with(density) {
        40.dp.toPx()
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(208.dp)
                .fillMaxWidth()
                .background(color = backgroundColor, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(144.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(size = 0.dp)
                    )
                    .drawBehind {
                        val heightMax = size.height
                        val dashWidth = 6.dp.toPx()
                        val gapWidth = 4.dp.toPx()

                        for (i in 0..maxGridValue step gridStep) {
                            val y = heightMax - (i / maxGridValue.toFloat()) * heightMax
                            var x = 0f
                            while (x < size.width) {
                                drawLine(
                                    color = gridColor,
                                    start = Offset(x, y),
                                    end = Offset((x + dashWidth).coerceAtMost(size.width), y),
                                    strokeWidth = 1.dp.toPx()
                                )
                                x += dashWidth + gapWidth
                            }
                        }
                    }
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                        )
                        .pointerInput(Unit) {
                            detectTapGestures { tapOffset ->
                                selectedPoint = ptsState.firstOrNull { (_, pointOffset) ->
                                    (tapOffset - pointOffset).getDistance() < clickRadiusPx
                                }
                            }
                        }
                ) {

                    val radiusPx = 6.dp.toPx()
                    val widthStep = size.width / (statistics.size - 1)
                    val heightMax = size.height

                    val points = statistics.entries.mapIndexed { index, entry ->
                        val x = index * widthStep
                        val y = heightMax - (entry.value / maxGridValue.toFloat()) * heightMax
                        entry.key to Offset(x, y)
                    }
                    ptsState = points

                    for (i in 0 until points.size - 1) {
                        drawLine(
                            color = lineColor,
                            start = points[i].second,
                            end = points[i + 1].second,
                            strokeWidth = 8f
                        )
                    }

                    points.forEach { (date, point) ->
                        drawCircle(
                            color = Color.White,
                            radius = radiusPx - 3f,
                            center = point,
                            )

                        drawCircle(
                            color = Color.Red,
                            radius = radiusPx,
                            center = point,
                            style = Stroke(width = 12f)
                        )

                        drawIntoCanvas { canvas ->
                            val dateLayoutResult = textMeasurer.measure(
                                text = date,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 11.sp,
                                    lineHeight = 11.sp,
                                    color = Color(color = 0XFFA7A7B1)
                                )
                            )
                            val textY = size.height + 12.dp.toPx()
                            drawText(
                                textLayoutResult = dateLayoutResult,
                                topLeft = Offset(
                                    x = point.x - 12.dp.toPx(),
                                    y = textY
                                )
                            )
                        }

                        selectedPoint?.let { (date, pointOffset) ->
                            val visitorsCount = statistics.getValue(date)
                            val height = 72.dp

                            val bottomOfWindow = pointOffset.y - height.toPx() / 2
                            val topOfGap =
                                pointOffset.y - radiusPx - 2.dp.toPx()
                            val bottomOfGap =
                                pointOffset.y + radiusPx + 2.dp.toPx()

                            val upperDash = Path().apply {
                                moveTo(pointOffset.x, bottomOfWindow)
                                lineTo(pointOffset.x, topOfGap)
                            }

                            val lowerDash = Path().apply {
                                moveTo(pointOffset.x, bottomOfGap)
                                lineTo(pointOffset.x, size.height)
                            }

                            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                            if (visitorsCount != 0) {
                                drawPath(
                                    path = upperDash,
                                    color = lineColor,
                                    style = Stroke(
                                        width = 0.5.dp.toPx(),
                                        pathEffect = dashEffect,
                                        cap = StrokeCap.Round
                                    )
                                )

                                drawPath(
                                    path = lowerDash,
                                    color = lineColor,
                                    style = Stroke(
                                        width = 1.dp.toPx(),
                                        pathEffect = dashEffect,
                                        cap = StrokeCap.Round
                                    )
                                )
                            } else {
                                drawPath(
                                    path = upperDash,
                                    color = lineColor,
                                    style = Stroke(
                                        width = 1.dp.toPx(),
                                        pathEffect = dashEffect,
                                        cap = StrokeCap.Round
                                    )
                                )
                            }

                            drawPointWindow(
                                date = Utils.parseDate(date),
                                visitors = visitorsCount,
                                pointOffset = pointOffset,
                                textMeasurer = textMeasurer
                            )
                        }
                    }
                }
            }
        }
    }
}


fun DrawScope.drawPointWindow(
    date: String,
    textMeasurer: TextMeasurer,
    visitors: Int,
    width: Dp = 128.dp,
    height: Dp = 72.dp,
    cornerRadius: Dp = 16.dp,
    verticalPadding: Dp = 16.dp,
    pointOffset: Offset
){

    val windowWidth = width.toPx()
    val windowHeight = height.toPx()
    val windowCornerRadius = cornerRadius.toPx()

    val windowOffsetX = pointOffset.x - windowWidth / 2
    val windowOffsetY = (pointOffset.y - windowHeight - 8.dp.toPx()).coerceAtLeast(0f) - verticalPadding.toPx()


    drawRoundRect(
        color = Color.White,
        topLeft = Offset(windowOffsetX, windowOffsetY),
        size = Size(windowWidth, windowHeight),
        cornerRadius = CornerRadius(windowCornerRadius, windowCornerRadius),
        style = Fill
    )

    drawRoundRect(
        color = Color(color = 0XFFA7A7B1),
        topLeft = Offset(windowOffsetX, windowOffsetY),
        size = Size(windowWidth, windowHeight),
        cornerRadius = CornerRadius(windowCornerRadius, windowCornerRadius),
        style = Stroke(width = 2.dp.toPx())
    )

    drawIntoCanvas { canvas ->
        val visitorsLayoutResult = textMeasurer.measure(
            text = Utils.pluralizeVisitors(visitors),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                lineHeight = 16.sp,
                color = Color(0XFFFF2E00)
            )
        )

        drawText(
            textLayoutResult = visitorsLayoutResult,
            topLeft = Offset(
                x = windowOffsetX + 16.dp.toPx(),
                y = windowOffsetY + 16.dp.toPx()
            )
        )

        val dateLayoutResult = textMeasurer.measure(
            text = date,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                color = Color(color = 0XFFA7A7B1)
            )
        )

        drawText(
            textLayoutResult = dateLayoutResult,
            topLeft = Offset(
                x = windowOffsetX + 16.dp.toPx(),
                y = windowOffsetY + 40.dp.toPx()
            )
        )
    }
}