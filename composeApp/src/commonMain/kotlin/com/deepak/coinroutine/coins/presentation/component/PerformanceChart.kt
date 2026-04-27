package com.deepak.coinroutine.coins.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.deepak.coinroutine.coins.presentation.UiChartState
import com.deepak.coinroutine.theme.LocalCoinRoutineColorsPalette

@Composable
fun PerformanceChart(
    modifier: Modifier = Modifier,
    nodes: List<Double>,
    profitColor: Color,
    lossColor: Color
) {
    if (nodes.isEmpty()) return

    val max = nodes.maxOrNull() ?: return
    val min = nodes.minOrNull() ?: return

    val lineColor = if (nodes.last() > nodes.first()) profitColor else lossColor

    Canvas(
        modifier = modifier.fillMaxWidth()
    ) {
        val path = androidx.compose.ui.graphics.Path()
        nodes.forEachIndexed { index, value ->
            val x = index * (size.width / (nodes.size - 1))
            val y = size.height * (1 - ((value - min) / (max - min)).toFloat())

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

@Composable
fun CoinChartDialog(
    uiChartState: UiChartState,
    onDismiss: () -> Unit
) {

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "24th Price chart for ${uiChartState.coinName}",

                )
        },
        text = {
            if (uiChartState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else {
                PerformanceChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    nodes = uiChartState.sparkLine,
                    profitColor = LocalCoinRoutineColorsPalette.current.profitGreen,
                    lossColor = LocalCoinRoutineColorsPalette.current.lossRed
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(
                    text = "Close"
                )
            }
        }
    )
}