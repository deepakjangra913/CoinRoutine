package com.deepak.coinroutine.portfolio.presentation

/**
 * UI representation of a coin item in the user's portfolio.
 *
 * @property id Unique identifier of the coin.
 * @property name Display name of the coin.
 * @property iconUrl URL for the coin's icon image.
 * @property amountInUnitText Formatted string of the amount owned in coin units (e.g., "0.5 BTC").
 * @property amountInFiatText Formatted string of the current value in fiat currency (e.g., "$30,000").
 * @property performancePercentText Formatted string of the percentage performance (e.g., "+5.2%").
 * @property isPositive Whether the performance percentage is positive or negative.
 */
data class UiPortfolioCoinItem(
    val id: String,
    val name: String,
    val iconUrl: String,
    val amountInUnitText: String,
    val amountInFiatText: String,
    val performancePercentText: String,
    val isPositive: Boolean
)
