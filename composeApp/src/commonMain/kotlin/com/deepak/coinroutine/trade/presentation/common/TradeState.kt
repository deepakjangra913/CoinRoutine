package com.deepak.coinroutine.trade.presentation.common

import org.jetbrains.compose.resources.StringResource

/**
 * Represents the UI state for the Buy or Sell transaction screens.
 *
 * @property isLoading Indicates if the transaction data or details are being loaded.
 * @property error An optional [StringResource] representing an error message to display.
 * @property availableAmount A formatted string showing the available balance (cash or coin amount).
 * @property amount The current amount entered by the user for the transaction.
 * @property coin The [UiTradeCoinItem] representing the coin being traded.
 */
data class TradeState(
    val isLoading: Boolean = false,
    val error: StringResource? = null,
    val availableAmount: String = "",
    val amount: String = "",
    val coin: UiTradeCoinItem? = null
)
