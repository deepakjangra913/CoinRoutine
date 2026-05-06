package com.deepak.coinroutine.coins.presentation

/**
 * UI representation of a coin item for display in a list.
 *
 * @property id Unique identifier of the coin.
 * @property name Display name of the coin.
 * @property symbol Ticker symbol of the coin.
 * @property iconUrl URL for the coin's icon.
 * @property formattedPrice Current price formatted as a fiat string.
 * @property formattedChange 24h price change formatted as a percentage string.
 * @property isPositive Whether the 24h change is positive or negative.
 */
data class UiCoinListItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
)
