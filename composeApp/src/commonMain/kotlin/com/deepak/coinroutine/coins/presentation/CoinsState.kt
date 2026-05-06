package com.deepak.coinroutine.coins.presentation

import androidx.compose.runtime.Stable
import org.jetbrains.compose.resources.StringResource

/**
 * Represents the UI state for the Coins List screen.
 *
 * @property error An optional [StringResource] representing an error message to display.
 * @property coins The list of [UiCoinListItem]s to be displayed.
 * @property uiChartState The state for the bottom sheet chart, or null if hidden.
 */
@Stable
data class CoinsState(
    val error: StringResource? = null,
    val coins: List<UiCoinListItem> = emptyList(),
    val uiChartState: UiChartState? = null
)

/**
 * Represents the state of the coin performance chart.
 *
 * @property sparkLine List of price points to plot on the chart.
 * @property isLoading Indicates if the chart data is being loaded.
 * @property coinName The name of the coin for which the chart is displayed.
 */
@Stable
data class UiChartState(
    val sparkLine: List<Double> = emptyList(),
    val isLoading: Boolean = false,
    val coinName: String = ""
)