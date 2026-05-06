package com.deepak.coinroutine.portfolio.presentation

import org.jetbrains.compose.resources.StringResource

/**
 * Represents the UI state for the Portfolio screen.
 *
 * @property portfolioValue The total value of the user's portfolio as a formatted string.
 * @property cashBalance The available cash balance as a formatted string.
 * @property showBuyButton Whether the buy button should be visible in the UI.
 * @property isLoading Indicates if the portfolio data is currently being loaded.
 * @property error An optional [StringResource] representing an error message to display if loading fails.
 * @property coins The list of [UiPortfolioCoinItem]s to be displayed in the portfolio.
 */
data class PortfolioState(
    val portfolioValue: String = "",
    val cashBalance: String = "",
    val showBuyButton: Boolean = false,
    val isLoading: Boolean = false,
    val error: StringResource? = null,
    val coins: List<UiPortfolioCoinItem> = emptyList()
)
