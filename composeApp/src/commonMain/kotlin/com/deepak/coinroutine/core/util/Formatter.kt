package com.deepak.coinroutine.core.util

/**
 * Formats a numerical amount into a fiat currency string representation (e.g., "$1,234.56").
 * Implementation is platform-specific.
 *
 * @param amount The numerical value to format.
 * @param showDecimal Whether to include decimal places in the formatted string.
 * @return A formatted fiat currency string.
 */
expect fun formatFiat(amount: Double, showDecimal: Boolean = true): String

/**
 * Formats a coin amount with its ticker symbol (e.g., "0.5 BTC").
 * Implementation is platform-specific.
 *
 * @param amount The numerical amount of the coin.
 * @param symbol The ticker symbol of the cryptocurrency.
 * @return A formatted string showing the amount and symbol.
 */
expect fun formatCoinUnit(amount: Double, symbol: String): String

/**
 * Formats a numerical value as a percentage string (e.g., "5.2%").
 * Implementation is platform-specific.
 *
 * @param amount The numerical value to format as a percentage.
 * @return A formatted percentage string.
 */
expect fun formatPercentage(amount: Double): String
